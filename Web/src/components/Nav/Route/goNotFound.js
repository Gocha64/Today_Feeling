import { useNavigate } from 'react-router-dom';
const goNotFound = () => {
    const navigate = useNavigate();

    return (() => { useNavigate('/'); }
    );
}

export default goNotFound;