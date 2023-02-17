import { useNavigate } from 'react-router-dom';

const goJoin = () => {
    const navigate = useNavigate();

    return (() => { useNavigate('/Join'); }
    );
}

export default goJoin;