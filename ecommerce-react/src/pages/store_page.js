import React, { Component } from "react"
import '../styles/store_page.css'
import { Row, Container, Card, Button, Col } from 'react-bootstrap'

const ItemCard = ({ item , handleSelect }) => {
    return (
        <Col className="col-3" >
            <Card id={item.id} className="store-card">
                <Card.Body>
                    <Card.Title style={{ fontSize: "16px", height: "80px" }}>
                        {item.title}
                        <br />
                        {item.price.toFixed(2)}
                    </Card.Title>
                    <Card.Img src={item.image} style={{ height: "100px" }} />
                    <Card.Text className="card-text">
                        {item.description}
                    </Card.Text>
                </Card.Body>
                <Card.Footer>
                    <Button onClick={() => { handleSelect(item.title, item.id, item.image, item.category, item.price) }}
                        style={{ fontSize: "16px" }}>Select Item
                        </Button>
                </Card.Footer>
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

    state = {
        title: "",
        price: 0.00,
        quantity: 1,
        itemId: 0,
        category: "",
        image: ""
    }

    selectHandler = (itemTitle, itemId, itemImage, itemCategory, itemPrice) => {
        console.log("selectHandler reached");
        this.setState({
            title: itemTitle,
            itemId: itemId,
            image: itemImage,
            category: itemCategory,
            price: itemPrice
        });
        console.log(this.state.price);
    }

    quantityHandler = (event) => {
        this.setState({ quantity: event.target.value });
        console.log(this.state.quantity)
    }

    render() {
        let { items, handleAdd, cart } = this.props
        return (
            <Container className="Store-page" style={{ height: "100vh" }}>
                <Row style={{ fontSize: "35px", marginBottom: "5px" }}>Storefront</Row>
                <div className="Store-grid">
                    {/* component holding all items */}
                    <Container className="main item-display">
                        {items.map((item, i) => {
                            return <ItemCard item={item} key={i} handleSelect={this.selectHandler} />
                        })}
                    </Container>
                    {/* sidebar component for quantity and item name with add to cart button */}
                    <Container className="sidebar" style={{ 
                        alignItem: "center"
                    }}>
                        {/* select input for category */}
                        <Row>
                            <select style={{ fontSize: "20px", margin: "10px" }}> // Category selection
                                <option selected disabled> Select a Category </option>
                                <option value="men clothing" > Men's Clothing </option>
                                <option value="women clothing"> Women's Clothing </option>
                                <option value="jewelry"> Jewelry </option>
                                <option value="electronics"> Electronics </option>
                            </select>
                        </Row>
                        <hr style={{ marginLeft: "40px", marginRight: "40px" }} />
                        {/* item title display when selected */}
                        <Row><img src={this.state.image} style={{height:"150px", margin:"10px"}}></img></Row>
                        <Row>
                            <textarea type="text" className="title-box" value={this.state.title} readOnly />
                        </Row>
                        {/* quantity of items to add to cart */}
                        <Row>
                            <label style={{ fontSize: "20px", margin: "5px" }}>Quantity:</label>
                            <input type="number" min="1" step="1" defaultValue="1" onChange={this.quantityHandler} onKeyDown="return false"
                                style={{ fontSize: "20px", textAlign: "center", width: "50px" }} />
                        </Row>
                        {/* add to cart button */}
                        <Row>
                            <Button onClick={() => {
                                handleAdd(this.state.title,
                                    this.state.price,
                                    this.state.quantity,
                                    this.state.itemId,
                                    this.state.category)
                            }}
                                style={{ fontSize: "20px", margin: "10px" }} >Add to Cart</Button>
                        </Row>
                    </Container>
                </div>
            </Container>
        )
    }
}

export default StorePage