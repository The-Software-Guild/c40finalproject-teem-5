import React, { Component } from "react"
import '../styles/store_page.css'
import { Table, Row, Container, Card, CardDeck, Button, CardColumns, Col } from 'react-bootstrap'

const ItemCard = ({ item }) => {
    return (
        <Col className="col-3" >
            <Card style={{ backgroundColor: "white", minHeight: "340px", maxWidth: "350px", borderStyle: "outset", padding: "5px", margin: "5px" }}>
                <Card.Body>
                    <Card.Title style={{ fontSize: "16px", height: "70px" }}>
                        {item.title}
                        <br />
                        {item.price.toFixed(2)}
                    </Card.Title>
                    <Card.Img src={item.image} style={{ height: "100px" }} />
                    <Card.Text style={{ border: "groove", height: "90px", fontSize: "14px", overflow: "scroll", overflowX: "hidden" }}>
                        {item.description}
                        <br />
                        {item.category}
                    </Card.Text>
                </Card.Body>
                <Card.Footer><Button>Select Item</Button></Card.Footer>
            </Card>
        </Col>
    );
}


class StorePage extends Component {

    static defaultsProps = {
        items: [{
            id: 0,
            title: "test product",
            price: 1.99,
            description: "lorem ipsum set",
            image: "https://i.pravatar.cc",
            category: "electronic"
        }]
    }

    render() {
        let { items } = this.props
        return (
            <Container className="Store-page" style={{ height: "100vh" }}>
                <Row style={{ fontSize: "35px" }}>Storefront</Row>
                <div className="Store-grid">
                    <Row className="main" style={{
                        display: "flex", flexDirection: "row", flexWrap: "wrap",
                        border: "groove", overflow: "scroll", overflowX: "hidden", maxHeight: "85vh"
                    }}>
                        {items.map((item, i) => {
                            return <ItemCard item={item} key={i} />
                        })}
                    </Row>
                    <Row className="sidebar" style={{ alignItem: "center" }}>
                        <select style={{ fontSize: "20px" }}>
                            <option selected disabled> Select a Category </option>
                            <option value="men clothing" > Men's Clothing </option>
                            <option value="women clothing"> Women's Clothing </option>
                            <option value="jewelry"> Jewelry </option>
                            <option value="electronics"> Electronics </option>
                        </select>
                    </Row>
                </div>
            </Container>
        )
    }
}

export default StorePage