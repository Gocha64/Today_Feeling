import React, {
    useCallback, useState
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
import _Image from 'image-js';

function CameraComponent() {

    //이미지 state
    const [img, setImg] = useState(null);
    const [imgGrey, setGrey] = useState(null);
    // const [xmin, setXmin] = useState(null);
    // const [xmax, setXmax] = useState(null);
    // const [ymin, setYmin] = useState(null);
    // const [ymax, setYmax] = useState(null);

    // const XminHandler = (c) => {
    //     setXmin(c);
    // }
    // const XmaxHandler = (c) => {
    //     setXmax(c);
    // }
    // const YminHandler = (c) => {
    //     setYmin(c);
    // }
    // const YmaxHandler = (c) => {
    //     setYmax(c);
    // }

    // const canvas = useRef(null);
    // const ctx = canvas.current.getContext('2d');

    // const CropImg = () => {
    //     img.onload = function () {
    //         ctx.drawImage(img, 150, 200, 500, 300, 60, 60, 500, 300);
    //     }
    // }
    const MODEL_URL = "jsmodel/model.json";

    function imgSet() {
        console.log("2")
        _Image.load(img).then(function (image) {
            image.grey();
            image.resize({ width: 48, height: 48 })
            setGrey(image);
        })
        return new Promise(function (resolve, reject) {
            resolve();
            reject(new Error("Request is failed"));
        });
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

    const setImageHandler = () => {
        console.log("1")
        let imageSrc = webcamRef.current.getScreenshot();
        setImg(imageSrc)
        return new Promise(function (resolve, reject) {
            resolve();
            reject();
        });
    }

    //캡쳐
    const capture = useCallback(() => {

        setImageHandler().then(imgSet()).then(loadModel());

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

        let a = tf.browser.fromPixels(imgGrey, 1)
        let yhat_valid = model.predict(tf.expandDims(a, axis))
        axis = -1;

        yhat_valid = yhat_valid.argmax(axis)
        console.log(emotion_label_to_text[yhat_valid[0]])

        console.log(yhat_valid[0])
    }

    return (
        <div>
            {img === null ? (
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
                                color: 'white',
                                // objectFit: 'cover',
                                // position: 'absolute',
                            }}
                        />
                        <button onClick={capture}>Capture photo</button>
                    </div>
                </div>
            ) : (
                <div>
                    <img src={img} alt="screenshot" />
                    <button onClick={() => setImg(null)}>Retake</button>

                    {/* <div>
                        <CroppedImage src={img} alt="cropped Image" x={20} y={20} cropHeight={20} cropWidth={20} />
                    </div> */}
                </div>

            )}
        </div>
    )
};

export default CameraComponent;