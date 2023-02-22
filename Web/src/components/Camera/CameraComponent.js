import React, {
    useCallback, useState, useEffect
    // , useRef 
} from 'react';
import Webcam from 'react-webcam';
import {
    // CameraOptions,
    useFaceDetection
} from 'react-use-face-detection';
import FaceDetection from '@mediapipe/face_detection';
import { Camera } from '@mediapipe/camera_utils';
import * as tf from '@tensorflow/tfjs';
import axios from 'axios';
import { useSelector } from 'react-redux';

function CameraComponent() {
    const user = useSelector((state) => state.user);
    const server_song_url = 'http://218.232.159.156:10081/emotion/recommend';
    const [image, setImage] = useState(null);
    const [imgSrc, setImgSrc] = useState("");
    const [emotionNumber, setEmotionNumber] = useState(null);
    const [emotion, setEmotion] = useState("");

    const MODEL_URL = "jsmodel/model.json";

    const imgSet = () => {
        {
            return loadModel();
        }
    }

    //웹 카메라 detecting 설정
    const { webcamRef, boundingBox, isLoading, detected, facesDetected } = useFaceDetection({
        faceDetectionOptions: {
            model: 'short',

        },
        faceDetection: new FaceDetection.FaceDetection({
            locateFile: (file) => `https://cdn.jsdelivr.net/npm/@mediapipe/face_detection/${file}`,
        }),
        camera: ({ mediaSrc, onFrame }
            // : CameraOptions
        ) =>
            new Camera(mediaSrc, {
                onFrame,
                width: 1280,
                height: 720,
            }),
    });

    const emotionInputData = {
        user_id: `${user.id}`,
        emotion_data: emotionNumber,
    }

    const setImageHandler = () => {
        console.log("1")
        const imageSrc = webcamRef.current.getScreenshot({ width: 1280, height: 800 });
        console.log(typeof (imageSrc));
        setImgSrc(imageSrc);

        const imgTmp = new Image(48, 48);
        imgTmp.src = imgSrc;
        setImage(imgTmp);
        console.log(image);
    }

    //캡쳐
    const capture = useCallback(() => {
        setImageHandler()
        return imgSet()
    }, [webcamRef]);

    async function loadModel() {
        const model = await tf.loadLayersModel(MODEL_URL);

        const emotion_label_to_text = {
            0: 'anger',
            1: 'fear',
            2: 'happiness',
            3: 'sadness',
            4: 'surprise',
            5: 'neutral'
        }
        let axis = 0;
        if (image !== null) {
            let a = tf.browser.fromPixels(image, 1);
            console.log('a = ' + a);

            let yhat_valid = model.predict(tf.expandDims(a, axis))
            axis = -1;
            yhat_valid.print();
            yhat_valid = tf.argMax(yhat_valid, axis);


            const values = yhat_valid.dataSync();
            const arr = Array.from(values);

            const resultKey = arr[0];
            setEmotionNumber(resultKey);
            const result = emotion_label_to_text[resultKey];
            setEmotion(String(result));
            console.log(emotion);
            GetSong();
        }
        else {
            imgSet();
        }
    }

    const GetSong = () => {
        axios.get(server_song_url, JSON.stringify(emotionInputData), {
            headers: {
                "Content-Type": 'application/json',
            },
        },
            { withCredentials: true }
        ).then(res => {
            //로그인 성공
            if ((res.data.result) !== "authentication failed" || (res.data.result) !== "undefined error") {
                // sessionStorage.setItem('user_id', user_id);
                console.log('노래 가져오기 성공');
                console.log("res.data.result" + (res.data));
            }
            //노래 불러오기 실패
            else {
                console.log(res.data.result);
            }
        })
            .catch(error => {
                console.log("error" + error);
            }
            )
    }

    useEffect(() => {
        console.log(image);
        console.log(imgSrc);
    }, [imgSrc]);

    return (
        <div>
            {imgSrc === "" ? (
                <div>
                    <br />
                    <br />
                    <br />
                    <br />
                    <p>{`Loading: ${isLoading}`}</p>
                    <p>{`Face Detected: ${detected}`}</p>
                    <p>{`Number of faces detected: ${facesDetected}`}</p>
                    <div style={{ width: '100%', height: '500px', position: 'relative' }}>
                        {boundingBox.map((box, index) => (
                            <div
                                key={`${index + 1}`}
                                style={{
                                    border: '4px solid red',
                                    position: 'absolute',
                                    top: `${box.yCenter * 90}%`,
                                    left: `${box.xCenter * 100}%`,
                                    width: `${box.width * 100}%`,
                                    height: `${box.height * 120}%`,
                                    zIndex: 1,
                                }}
                            />
                        ))}
                        <Webcam
                            ref={webcamRef} audio={false} screenshotFormat="image/jpeg"
                            forceScreenshotSourceSize
                            style={{
                                height: '100%',
                                width: '100%',
                                // objectFit: 'cover',
                                // position: 'absolute',
                            }}
                        />
                        <button onClick={capture}>Capture photo</button>
                    </div>
                </div>
            ) : (
                <div>
                    <img src={imgSrc} alt="screenshot" />
                    <button onClick={() => setImgSrc("")}>Retake</button>
                </div>

            )}
        </div>
    )
};

export default CameraComponent;