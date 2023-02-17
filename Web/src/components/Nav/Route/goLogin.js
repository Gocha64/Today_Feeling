import { useNavigate } from 'react-router-dom';

const goLogin = () => {
    const navigate = useNavigate();

    return (() => { useNavigate('/Login'); }
    );
}

export default goLogin;