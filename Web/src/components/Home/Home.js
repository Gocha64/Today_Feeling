import { useNavigate } from "react-router-dom";
function Home() {
    const navigate = useNavigate();
    return (
        <div>
            <div>
                그림
            </div>
            <br />
            <br />
            <div>
                설명
            </div>
            <div>
                <button onClick={() => { navigate('/Camera'); }}> 웹캡 시작</button>
            </div>
        </div >
    );
};

export default Home;