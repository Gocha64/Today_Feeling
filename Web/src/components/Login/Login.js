import React, { useState, useEffect } from 'react';
import { useDispatch } from "react-redux";
import { userLogIn } from "../Reducer/userSlice.js";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../../styles/Login.css"

function Login() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [user_id, setId] = useState('');
    const [user_pw, setPw] = useState('');

    const [msg, setMsg] = useState('');

    const server_login_url = 'http://218.232.159.156:10081/member/login';
    const server_search_url = `http://218.232.159.156:10081/member/search?userId=${user_id}`;

    // const session_id = '<%=(String)session.getAttribute("uid")%>';

    const userIdHandler = (e) => {
        setId(e.target.value);
    }

    const userPwHandler = (e) => {
        setPw(e.target.value);
    }

    //Data 양식
    const userLoginData = {
        userId: user_id,
        userPw: user_pw
    }

    const userLogInTmp = {
        userId: user_id
    }

    useEffect(() => {
        if (msg) {
            setMsg("");
        }
    }, [msg])

    //로그인 버튼 클릭
    const onClickLogin = (e) => {
        console.log('로그인 시도');

        //리로드 막음
        e.preventDefault();
        if (!user_id) {
            return alert("Id를 입력하세요");
        }
        else if (!user_pw) {
            return alert("비밀번호를 입력하세요");
        }

        axios.post(server_login_url, JSON.stringify(userLoginData), {
            headers: {
                "Content-Type": 'application/json'
            },
        }).then(res => {
            //로그인 성공
            if (res.data.result === 'success') {
                // sessionStorage.setItem('session_id', res.headers['session']);
                // console.log(sessionStorage.getItem('session_id'));
                console.log(res.headers);
                alert('로그인 성공');
                GetInfo()
                //Home으로 이동
                navigate('/');
            }
            //로그인 실패
            else {
                alert('로그인 실패');
                setMsg("아이디와 비밀번호를 다시 확인해주세요");
                console.log(res.data.result);
            }
        })
            .catch(error => {
                console.log(error);
            }
            )
    }

    //회원정보 가져오기
    const GetInfo = () => {
        axios.get(server_search_url, {
            headers: {
                "Content-Type": 'application/json',
            },
        },
            { withCredentials: true, }
        ).then(res => {
            //로그인 성공
            if ((res.data.result) !== "authentication failed" || (res.data.result) !== "undefined error") {
                // sessionStorage.setItem('user_id', user_id);
                alert('회원 정보 불러오기 성공');
                console.log(userLogInTmp);
                console.log("res.data.result" + (res.data));
                dispatch(userLogIn(res.data));
                //Home으로 이동
                navigate('/');
            }
            //회원정보 불러오기 실패
            else {
                alert('회원 정보 불러오기 실패');
                setMsg("아이디와 비밀번호를 다시 확인해주세요");
                console.log(res.data.result);
            }
        })
            .catch(error => {
                console.log("error" + error);
            }
            )
    }


    return (
        <div>
            <h2>Login Page</h2>
            {/* 아이디 */}
            <div>
                <label htmlFor='input_id'>ID : </label>
                <input type='text' name='user_id' placeholder='아이디를 입력하세요' value={user_id} onChange={userIdHandler} />
            </div>
            {/* 비밀번호 */}
            <div>
                <label htmlFor='input_pw'>PW : </label>
                <input type='password' name='user_pw' placeholder='비밀번호를 입력하세요' value={user_pw} onChange={userPwHandler} />
            </div>
            <span>
                <button type='button' onClick={onClickLogin}>Login</button>
            </span>
            <span>
                <span type='link' onClick={() => { navigate('/SignIn'); }}>SignIn</span>
            </span>
            <div className='msg'>
                {msg}
            </div>
        </div>
    );
};
export default Login;