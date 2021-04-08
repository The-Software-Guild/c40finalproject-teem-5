import React, { Component } from 'react'
import { Button, Container, Row, Form } from 'react-bootstrap'
import '../styles/login_page.css'

const StateOption = ({ state }) => {
    return (
        <option value={state}>{state}</option>
    )
}

class LoginPage extends Component {

    states = [
        "AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FM", "FL",
        "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH",
        "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
        "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY"
    ]

    state = {
        customer: {
            name: "",   // max 50 char
            email: "",  // max 50 char
            phone: ""   // max 10 char and needs regex validation (?)
        },
        address: {
            street: "", // max 30 char
            city: "",   // max 30 char
            state: "",  // max 2 char (should be handled by <select> input method)
            postal: "", // max 10 char
            country: "" // max 30 char
        }
    }

    render() {
        let { customer, selectCustomer, address, selectAddress } = this.props
        return (
            <Container className="Login-page">
                <Row style={{ fontSize: "50px", color: "white" }}>Account</Row>
                <div className="half-grid">
                    <Container className="left">
                        <select value={customer} onChange={selectCustomer}
                            style={{ fontSize: "30px", width: "300px", marginBottom: "10px" }}>
                            <option selected disabled> Select a Customer </option>
                            <option value="1"> Customer 1 </option>
                            <option value="2"> Customer 2 </option>
                            <option value="3"> Customer 3 </option>
                        </select>
                    </Container>
                    <Container className="right">
                        <Container className="half-grid" style={{ border: "groove" }}>
                            <Container className="left sign-up">
                                <Form>
                                    <div style={{ color: "white", fontSize: "30px", margin: "10px" }}>Address</div>
                                    <Form.Row>
                                        <label className="customer-label">Street:</label>
                                        <input type="text" className="customer-input" required />
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">City:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">State:</label>
                                        <select type="text" className="customer-input">
                                            <option disabled selected>Select a State</option>
                                            {this.states.map((state, i) => {
                                                return <StateOption state={state} key={i} />
                                            })}
                                        </select>
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">Postal:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">Country:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                </Form>
                            </Container>
                            <Container className="right sign-up">
                                <Form>
                                    <div style={{ color: "white", fontSize: "30px", margin: "10px" }}>Customer</div>
                                    <Form.Row>
                                        <label className="customer-label">Name:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">Email:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                    <Form.Row>
                                        <label className="customer-label">Phone:</label>
                                        <input type="text" className="customer-input" />
                                    </Form.Row>
                                </Form>
                            </Container>
                        </Container>
                    </Container>
                </div>
            </Container>
        )
    }
}

export default LoginPage