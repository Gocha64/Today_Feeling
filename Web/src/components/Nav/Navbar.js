import { useNavigate } from "react-router-dom";
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar';
import { Button, Container } from 'react-bootstrap'
// import NavDropdown from 'react-bootstrap/NavDropdown';

import '../../styles/Navbar.css';

function NavigationBar() {
    const navigate = useNavigate();

    return (
        <Navbar collapseOnSelect id="Navbar" fixed="top" className="Navbar" bg="light" expand="lg" >
            <Container>
                <Navbar.Brand>Feeling_Manager</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav.Item id='Logo' className='nav-item'>
                        <Button onClick={() => { navigate('/'); }}>Home</Button>
                    </Nav.Item>
                    <Nav.Item id='Profile' className='nav-item'>
                        <Button onClick={() => { navigate('/Profile') }}>Profile</Button>
                    </Nav.Item>
                    <Nav.Item id='Login' className='nav-item'>
                        <Button onClick={() => { navigate('/Login'); }}>Login</Button>
                    </Nav.Item>
                    <Nav.Item id='Statistics' className='nav-item'>
                        <Button onClick={() => { navigate('/Statistics'); }}>Stat</Button>
                    </Nav.Item>
                    <Nav.Item id='SignIn' className='nav-item'>
                        <Button onClick={() => { navigate('/SignIn'); }}>SingIn</Button>
                    </Nav.Item>
                </Navbar.Collapse>
            </Container >
        </Navbar >
    );
};


export default NavigationBar;