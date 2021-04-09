import React from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import './checkout_page.css'



const ListedItem = ({ item, key }) => {

    return (

        <Card style={{ width: '18rem', backgroundColor: "lightblue", margin:"5px"}} >
            <Card.Body>
                <Card.Title>{item.title}</Card.Title>
                <div>
                    <br />
                    Price: {item.price}
                    <br />
                    Quantity: {item.quantity}
                    <br />
                    Cost For Item(s): {item.totalForCard}
                    <br />
                    <br />
                </div>
            </Card.Body>
        </Card>

    );
}

class CheckoutPage extends React.Component {

    constructor(props) {
        super(props);

        this.state.replaceItems = {
            title: null,
            price: null,
            quantity: null,
            itemId: null,
            category: null,
            totalForCard: null
        }
    }

    state = {
        replaceItems : [
        {
            title: null,
            price: null,
            quantity: null,
            itemId: null,
            category: null,
            totalForCard: null
        }]
    }

    handleTotalCalculation(){
        this.props.handleTotalCalculation();
    }

    handleDeleteCart(){
        
        this.setState({items:""});
        
        window.location.reload(false);
        alert("Cart Emptied");
    }
    

    componentDidMount(){
        this.handleTotalCalculation();
    }

    render() {
        let {items, currency, handleCurrencySelect, handleTestAxios, totalCost, handleTotalCalculation, handleClearItems} = this.props
        return (
            
            <Container className="gridForCheckout">
                <Row>
                    <Col>
                        <h1 className="text-center">Checkout</h1>
                    </Col>
                </Row>
                <hr />
                <Row style={{ display: "flex", justifyContent: "space-between" }}>
                    <Col style={{width:"70%"}} className="Cart-Display">
                        <span id="checkout_page" className="item-display">
                            {items.map((item, i) => {
                                return <ListedItem item={item} key={i} />
                            })}
                        </span>
                    </Col>
                    <Col style={{width:"30%"}}>
                        <h2>Cost in Selected Currency</h2>
                        <input type="text" value={totalCost} readOnly />
                        <hr />
                        <h2>Select Currency</h2>
                        <br />
                        <select value={currency} onChange={handleCurrencySelect}>
                            <option selected value="USD">USD</option>
                            <option value="CAD">CAD</option>
                            <option value="EUR">EUR</option>
                            <option value="GBP">GBP</option>
                            <option value="JPY">JPY</option>
                            <option value="CNY">CNY</option>
                        </select>
                        <hr />
                        <Button onClick={handleTestAxios}>
                            Purchase
                        </Button>
                        <hr />
                        <Button onClick={() => this.handleDeleteCart()}>
                            Empty Cart
                        </Button>
                    </Col>
                </Row>
            </Container>
        )
    }
}

export default CheckoutPage