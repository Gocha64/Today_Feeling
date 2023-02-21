import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';

import '../../styles/Profile.css'
import { userLogOut } from "../Reducer/userSlice";

function Profile() {
    const user = useSelector((state) => state.user);
    const dispatch = useDispatch();

    const navigate = useNavigate();

    const user_id = `${user.id}`;
    const user_name = `${user.name}`;
    const user_gender = `${user.sex}`;
    const user_email = `${user.email}`;
    const user_anger = `${user.anger}`;
    const user_fear = `${user.happiness}`;
    const user_hapiness = `${user.happiness}`;
    const user_sadness = `${user.sadness}`;
    const user_surprise = `${user.surprise}`;

    const Logout = () => {
        dispatch(userLogOut(user));
        navigate('/');
    }

    const reviseInfo = () => {
        navigate('/ReviseInfo');
    }

    return (
        <div>
            <h1>Profile Page</h1>
            <div>아이디 : {user_id}</div>
            <div>이름 : {user_name}</div>
            <div>성별 : {user_gender}</div>
            <div>이메일 : {user_email}</div>
            <div>화났을때 : {user_anger}</div>
            <div>두려울때 : {user_fear}</div>
            <div>행복할때 : {user_hapiness}</div>
            <div>슬플때 : {user_sadness}</div>
            <div>놀랐을때 : {user_surprise}</div>
            <button onClick={() => Logout()}>로그아웃</button>
            <button onClick={() => reviseInfo()}>회원정보수정</button>
        </div>
    )
}

export default Profile;