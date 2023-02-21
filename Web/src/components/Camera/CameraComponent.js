import React, { useCallback, useState, useRef } from 'react';
import Webcam from 'react-webcam';
import {
    // CameraOptions,
    useFaceDetection
} from 'react-use-face-detection';
import FaceDetection from '@mediapipe/face_detection';
import { Camera } from '@mediapipe/camera_utils';


function CameraComponent() {

    var Crop = require('tinycrop')
    //이미지 state
    const [img, setImg] = useState(null);

    const [xmin, setXmin] = useState(null);
    const [xmax, setXmax] = useState(null);
    const [ymin, setYmin] = useState(null);
    const [ymax, setYmax] = useState(null);

    const XminHandler = (c) => {
        setXmin(c);
    }
    const XmaxHandler = (c) => {
        setXmax(c);
    }
    const YminHandler = (c) => {
        setYmin(c);
    }
    const YmaxHandler = (c) => {
        setYmax(c);
    }

    // const canvas = useRef(null);
    // const ctx = canvas.current.getContext('2d');

    // const CropImg = () => {
    //     img.onload = function () {
    //         ctx.drawImage(img, 150, 200, 500, 300, 60, 60, 500, 300);
    //     }
    // }

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

    //캡쳐
    const capture = useCallback(() => {
        let imageSrc = webcamRef.current.getScreenshot();
        setImg(imageSrc);
    }, [webcamRef]);

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

                    {/* <canvas ref={canvas} width="1000px" height="500px">
                    </canvas> */}
                    {/* <div>{CropImg()}</div> */}

                    {/* <Pyodide /> */}
                </div>
            )}
        </div>
    )
};

export default CameraComponent;