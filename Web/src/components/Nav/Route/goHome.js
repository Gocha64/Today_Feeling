import { useNavigate } from 'react-router-dom';
const goHome = () => {
    const navigate = useNavigate();

    return (() => { useNavigate('/Home'); }
    );
}

export default goHome;