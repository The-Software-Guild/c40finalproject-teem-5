import React, { Component } from 'react'
import { Button, Container, Row } from 'react-bootstrap'
import '../styles/login_page.css'
class LoginPage extends Component {


    render() {
        let { customer, selectCustomer, address, selectAddress } = this.props
        return (
            <Container className="Login-page">
                <div className="Login-grid">
                    <div className="left">
                        <Container style={{ marginBottom: "10px" }}>Log In</Container>
                        <select value={customer} onChange={selectCustomer}
                            style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                            <option selected disabled> Select a Customer </option>
                            <option value="1"> Customer 1 </option>
                            <option value="2"> Customer 2 </option>
                            <option value="3"> Customer 3 </option>
                        </select>
                    </div>
                    <div className="right">
                    </div>
                </div>
            </Container>
        )
    }
}

export default LoginPage