import CameraComponent from "./CameraComponent";

function Camera() {
    return (
        <div>
            <div>
                <div src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs">

                </div>
                <div src="https://cdn.jsdelivr.net/npm/@tensorflow-models/blazeface">

                </div>
                <CameraComponent />
            </div>

        </div>
    )
    // < div >
    //         <div id='SongInput'>
    //             노래 재생
    //             <div>
    //                 <iframe width="560" height="315" src="https://www.youtube.com/embed/0AqQvq5lY2c" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
    //             </div>
    //         </div>
    //         <div>
    //             Camera
    //         </div>
}
export default Camera;