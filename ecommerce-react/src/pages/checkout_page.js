import React from 'react';
import {Card, Button, Container, Row, Col, Table} from 'react-bootstrap';
import axios from 'axios';


const ListedItem = ({ item, key }) => {

    return (

        <Card style={{ width: '18rem', backgroundColor: "lightblue" }} >
            <Card.Body>
                <Card.Title>{item.title}</Card.Title>
                <div>
                    <br />
                    Price: {item.price}
                    <br />
                    Quantity: {item.quantity}
                    <br />
                    <br />
                    <br />
                </div>
            </Card.Body>
        </Card>

    );
}

class CheckoutPage extends React.Component {



    render() {
        let { items, currency, handleCurrencySelect, handleTestAxios, totalCost,exchangeRate } = this.props
        return (
            <Container fluid>
                <Row>
                    <Col>
                        <h1 className="text-center">Checkout</h1>
                    </Col>
                </Row>
                <hr />
                <Row style={{ display: "flex", justifyContent: "space-between" }}>
                    <Col sm={8} className="Cart-Display">
                        <span id="checkout_page" className="App-page">
                            {items.map((item, i) => {
                                return <ListedItem item={item} key={i} />
                            })}
                        </span>
                    </Col>
                    <Col sm={4}>
                        <input type="text" value={totalCost} readOnly />
                        <hr />
                        <span>Select Currency</span>
                        <br />
                        <select value={currency} onChange={handleCurrencySelect}>
                            <option selected value="USD">USD</option>
                            <option value="CAD">CAD</option>
                            <option value="EUR">EUR</option>
                            <option value="GBP">GBP</option>
                            <option value="JPY">JPY</option>
                        </select>
                        <hr />
                        <Button onClick={handleTestAxios}>
                            Purchase
                   </Button>
                        <hr />
                    </Col>
                </Row>

                <Table className="table table-striped">
                    <thead>
                    <tr>
                        <td>Base Currency</td>
                        <td>CAD Rate</td>
                        <td>EUR Rate</td>
                        <td>GBP Rate</td>
                        <td>JPY Rate</td>
                        <td>CNY Rate</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{exchangeRate.base}</td>
                        <td>{exchangeRate.rates.CAD}</td>
                        <td>{exchangeRate.rates.EUR}</td>
                        <td>{exchangeRate.rates.GBP}</td>
                        <td>{exchangeRate.rates.JPY}</td>
                        <td>{exchangeRate.rates.CNY}</td>
                    </tr>
                    </tbody>
                </Table>
            </Container>
        )
    }
}

export default CheckoutPage