import { useNavigate } from 'react-router-dom';

const goCamera = () => {
    const navigate = useNavigate();

    return (() => { useNavigate('/Camera'); }
    );
}

export default goCamera;