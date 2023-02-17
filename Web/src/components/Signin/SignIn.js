// import React, { useState } from "react";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../../styles/SignIn.css"

function SignInPage() {
    const navigate = useNavigate();

    const [user_id, setId] = useState('');
    const [user_pw, setPw] = useState('');
    const [user_pw2, setPw2] = useState('');
    const [user_name, setName] = useState('');
    const [user_gender, setGender] = useState('');
    const [user_email, setEmail] = useState('');

    const [checkItems_anger, setCheckItems_anger] = useState([]);
    const [checkItems_fear, setCheckItems_fear] = useState([]);
    const [checkItems_happiness, setCheckItems_happiness] = useState([]);
    const [checkItems_sadness, setCheckItems_sadness] = useState([]);
    const [checkItems_surprise, setCheckItems_surprise] = useState([]);

    const [user_anger, setAnger] = useState('');
    const [user_fear, setFear] = useState('');
    const [user_happiness, setHappiness] = useState('');
    const [user_sadness, setSadness] = useState('');
    const [user_surprise, setSurprise] = useState('');

    const checkBoxList = ['클래식', '재즈', '팝', '트로트', '아이돌음악', '락', '발라드', '개임음악', '힙합', '기타']

    const replaceAt = (target, index, replacement) => {
        console.log(1);
        if (index >= target.length || index < 0) {
            return target;
        }
        console.log(2);
        return target.substring(0, index) + replacement + target.substring(index + 1);
    }

    const server_signin_url = 'http://218.232.159.156:10081/member/register';

    const [msg, setMsg] = useState('');

    const userIdHandler = (e) => {
        setId(e.target.value);
    }

    const userPwHandler = (e) => {
        setPw(e.target.value);
    }

    const userPw2Handler = (e) => {
        setPw2(e.target.value);
    }

    const userNameHandler = (e) => {
        setName(e.target.value);
    }

    const userGenderHandler = (e) => {
        setGender(e.target.value);
    }

    const userEmailHandler = (e) => {
        setEmail(e.target.value);
    }

    const handleSingleCheck_anger = (checked, id) => {
        if (checked) {
            setCheckItems_anger(prev => [...prev, id]);
        } else {
            setCheckItems_anger(checkItems_anger.filter((el) => el !== id));
        }
    };

    const handleSingleCheck_fear = (checked, id) => {
        if (checked) {
            setCheckItems_fear(prev => [...prev, id]);
        } else {
            setCheckItems_fear(checkItems_fear.filter((el) => el !== id));
        }
    };

    const handleSingleCheck_happiness = (checked, id) => {
        if (checked) {
            setCheckItems_happiness(prev => [...prev, id]);
        } else {
            setCheckItems_happiness(checkItems_happiness.filter((el) => el !== id));
        }
    };

    const handleSingleCheck_sadness = (checked, id) => {
        if (checked) {
            setCheckItems_sadness(prev => [...prev, id]);
        } else {
            setCheckItems_sadness(checkItems_sadness.filter((el) => el !== id));
        }
    };

    const handleSingleCheck_surprise = (checked, id) => {
        if (checked) {
            setCheckItems_surprise(prev => [...prev, id]);
        } else {
            setCheckItems_surprise(checkItems_surprise.filter((el) => el !== id));
        }
    };

    const setUserAngerHandler = () => {
        let tmp_anger = "0000000000";

        for (let i = 0; i < checkItems_anger.length; i++) {
            tmp_anger = replaceAt(tmp_anger, checkItems_anger[i], '1');
        }
        setAnger(tmp_anger);
    }

    const setUserFearHandler = () => {
        let tmp_fear = "0000000000";

        for (let i = 0; i < checkItems_fear.length; i++) {
            tmp_fear = replaceAt(tmp_fear, checkItems_fear[i], '1');
        }
        setFear(tmp_fear);
    }

    const setUserHappinessHandler = () => {
        let tmp_happiness = "0000000000";

        for (let i = 0; i < checkItems_happiness.length; i++) {
            tmp_happiness = replaceAt(tmp_happiness, checkItems_happiness[i], '1');
        }
        setHappiness(tmp_happiness);
    }

    const setUserSadnessHandler = () => {
        let tmp_sadness = "0000000000";

        for (let i = 0; i < checkItems_sadness.length; i++) {
            tmp_sadness = replaceAt(tmp_sadness, checkItems_sadness[i], '1');
        }
        setSadness(tmp_sadness);
    }

    const setUserSurpriseHandler = () => {
        let tmp_surprise = "0000000000";

        for (let i = 0; i < checkItems_surprise.length; i++) {
            tmp_surprise = replaceAt(tmp_surprise, checkItems_surprise[i], '1');
        }
        setSurprise(tmp_surprise);
    }

    const CheckEmail = (str) => {
        var reg_email = /^([0-9a-zA-Z_\\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
        if (!reg_email.test(str)) {
            return false;
        } else {
            return true;
        }
    }

    const CheckPw = () => {
        if (user_pw === user_pw2)
            return true;
        else
            return false;
    }

    const userSignIndata = {
        userId: user_id,
        userPw: user_pw,
        userName: user_name,
        userSex: user_gender,
        userEmail: user_email,
        anger: user_anger,
        fear: user_fear,
        happiness: user_happiness,
        sadness: user_sadness,
        surprise: user_surprise
    }

    useEffect(() => {
        if (msg) {
            setMsg("");
        }
    }, [msg])

    const onClickSignIn = (e) => {
        console.log('회원가입 시도');
        //리로드 막음
        e.preventDefault();

        setUserAngerHandler();
        setUserFearHandler();
        setUserHappinessHandler();
        setUserSadnessHandler();
        setUserSurpriseHandler();

        if (!user_id) {
            return alert("Id를 입력하세요");
        }
        else if (!user_pw) {
            return alert("비밀번호를 입력하세요");
        }
        else if (!CheckPw()) {
            return alert("비밀번호가 다릅니다");
        }
        else if (!user_name) {
            return alert("이름을 입력하세요");
        }
        else if (!user_gender) {
            return alert("성별을 입력하세요");
        }
        else if (!user_email) {
            return alert("이메일 입력하세요");
        }
        else if (!CheckEmail(user_email)) {
            return alert("이메일 양식을 확인하세요");
        }


        console.log("checkItems_anger = " + checkItems_anger);
        console.log("user_anger = " + user_anger);

        axios.post(server_signin_url, JSON.stringify(userSignIndata), {
            headers: {
                "Content-Type": 'application/json'
            },
        }).then(res => {
            //회원가입 성공
            if (res.data.result === 'success') {
                alert('회원가입 성공');
                //Home으로 이동
                navigate('/Login');
            }
            else if (res.data.result === 'overlaped ID') {
                alert('아이디가 중복되었습니다.');
            }
            //회원가입 실패
            else {
                alert('회원가입 실패');
                setMsg("아이디와 비밀번호를 다시 확인해주세요");
                console.log(res.data.result);
            }
        })
            .catch(error => {
                console.log(error);
            }
            )
    }

    return (
        <div>
            <h2>SignInPage</h2>
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
            <div>
                <label htmlFor='input_pwCheck'>PW check : </label>
                <input type='password' name='user_pw' placeholder='비밀번호를 다시 입력하세요' value={user_pw2} onChange={userPw2Handler} />
            </div>

            {/* 이름 */}
            <div>
                <label htmlFor='input_name'>Name : </label>
                <input type='text' name='user_name' placeholder='이름을 입력하세요' value={user_name} onChange={userNameHandler} />
            </div>
            {/* 성별 */}
            <div>
                <div>Gender : </div>
                <label htmlFor='input_gender_male'>Male</label>
                <input type='radio' name='user_gender' value={"1"} checked={user_gender === "1"} onChange={userGenderHandler} />
                <label htmlFor='input_gender_female'>Female</label>
                <input type='radio' name='user_gender' value={"2"} checked={user_gender === "2"} onChange={userGenderHandler} />
            </div>
            {/* 이메일 */}
            <div>
                <label htmlFor='input_email'>Email : </label>
                <input type='email' name='user_email' placeholder='이메일을 입력하세요' value={user_email} onChange={userEmailHandler} />
            </div>

            {/* 감정별 선호도조사 */}
            <div>
                <h2>감정상태 별 선호하는 장르</h2>

                {/* 화났을때 */}
                <br />
                <div className='checkbox-anger-group'>
                    <h2>화났을때</h2>
                    {
                        checkBoxList.map((item_anger, idx) => (
                            <div className='checkbox-anger' key={idx}>
                                <input type='checkbox' id={item_anger} onChange={(e) => handleSingleCheck_anger(e.target.checked, idx)} checked={checkItems_anger.includes(idx) ? 1 : 0} value={""} />
                                <label htmlFor={item_anger}>{item_anger}</label>
                            </div>
                        ))
                    }
                </div>

                {/* 무서울때 */}
                <br />
                <div className='checkbox-fear-group'>
                    <h2>무서울때</h2>
                    {
                        checkBoxList.map((item_fear, idx) => (
                            <div className='checkbox-fear' key={idx}>
                                <input type='checkbox' id={item_fear} onChange={(e) => handleSingleCheck_fear(e.target.checked, idx)} checked={checkItems_fear.includes(idx) ? 1 : 0} value={""} />
                                <label htmlFor={item_fear}>{item_fear}</label>
                            </div>
                        ))
                    }
                </div>

                {/* 행복할때 */}
                <br />
                <div className='checkbox-happiness-group'>
                    <h2>행복할때</h2>
                    {
                        checkBoxList.map((item_happiness, idx) => (
                            <div className='checkbox-happiness' key={idx}>
                                <input type='checkbox' id={item_happiness} onChange={(e) => handleSingleCheck_happiness(e.target.checked, idx)} checked={checkItems_happiness.includes(idx) ? 1 : 0} value={""} />
                                <label htmlFor={item_happiness}>{item_happiness}</label>
                            </div>
                        ))
                    }
                </div>

                {/* 슬플때 */}
                <br />
                <div className='checkbox-sadness-group'>
                    <h2>슬플때</h2>
                    {
                        checkBoxList.map((item_sadness, idx) => (
                            <div className='checkbox-sadness' key={idx}>
                                <input type='checkbox' id={item_sadness} onChange={(e) => handleSingleCheck_sadness(e.target.checked, idx)} checked={checkItems_sadness.includes(idx) ? 1 : 0} value={""} />
                                <label htmlFor={item_sadness}>{item_sadness}</label>
                            </div>
                        ))
                    }
                </div>

                {/* 놀랐을때 */}
                <br />
                <div className='checkbox-surprise-group'>
                    <h2>놀랐을때</h2>
                    {
                        checkBoxList.map((item_surprise, idx) => (
                            <div className='checkbox-surprise' key={idx}>
                                <input type='checkbox' id={item_surprise} onChange={(e) => handleSingleCheck_surprise(e.target.checked, idx)} checked={checkItems_surprise.includes(idx) ? 1 : 0} value={""} />
                                <label htmlFor={item_surprise}>{item_surprise}</label>
                            </div>
                        ))
                    }
                </div>
            </div>
            {/* 회원가입버튼 */}
            <span>
                <button type='button' onClick={onClickSignIn}>SignIn</button>
            </span>

            <div className='msg'>
                {msg}
            </div>
        </div>
    )
}

export default SignInPage;