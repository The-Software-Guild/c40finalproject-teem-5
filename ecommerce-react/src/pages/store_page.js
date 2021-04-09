import React, { Component } from "react"
import '../styles/store_page.css'
import { Row, Container, Card, Button, Col } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';

const ItemCard = ({ item, handleSelect }) => {
    return (
        <div className="col-3" >
            <div id={item.id} className="store-card">
                <div>
                    <div style={{ fontSize: "14px", height: "80px" }}>
                        {item.title}
                        <br />
                        ${item.price.toFixed(2)}
                    </div>
                    <img src={item.image} style={{ height: "100px" }} />
                    <div className="card-text">
                        {item.description}
                    </div>
                </div>
                <div>
                    <button onClick={() => { handleSelect(item.title, item.id, item.image, item.category, item.price) }}
                        style={{ fontSize: "16px", margin: "10px" }}>Select Item
                        </button>
                </div>
            </div>
        </div>
    );
}


class StorePage extends Component {

    static defaultsProps = {
        items: [{
            id: 0,
            title: "test product",
            price: 1.99,
            description: "lorem ipsum set",
            image: "",
            category: "electronic"
        }]
    }

    state = {
        title: "",
        price: 0.00,
        quantity: 1,
        itemId: 0,
        category: "",
        image: "",
        buttonStatus: true
    }

    selectHandler = (itemTitle, itemId, itemImage, itemCategory, itemPrice) => {
        console.log("selectHandler reached");
        this.setState({
            title: itemTitle,
            itemId: itemId,
            image: itemImage,
            category: itemCategory,
            price: itemPrice,
            buttonStatus: false
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
            <div className="Store-page">
                <div style={{ fontSize: "35px" }}>Storefront</div>
                <div className="Store-grid">
                    {/* component holding all items */}
                    <div className="item-display">
                        {items.filter(it => it.category.includes('men clothing')).map((filteredName,i) =>(
                            return <ItemCard item={filteredName} key={i} handleSelect={this.selectHandler} />
                            ))}
                    </div>
                    {/* sidebar component for quantity and item name with add to cart button */}
                    <div className="sidebar" style={{
                        alignItems: "center"
                    }}>
                        {/* select input for category */}
                        <div>
                            <select style={{ fontSize: "20px", margin: "10px" }}> // Category selection
                                <option selected disabled> Select a Category </option>
                                <option value="men clothing" > Men's Clothing </option>
                                <option value="women clothing"> Women's Clothing </option>
                                <option value="jewelry"> Jewelry </option>
                                <option value="electronics"> Electronics </option>
                            </select>
                        </div>
                        <hr style={{ marginLeft: "40px", marginRight: "40px" }} />
                        {/* item title display when selected */}
                        <div><img src={this.state.image} style={{ height: "150px", margin: "10px" }}></img></div>
                        <div>
                            <textarea type="text" className="title-box" value={this.state.title} readOnly />
                        </div>
                        {/* quantity of items to add to cart */}
                        <div>
                            <label style={{ fontSize: "20px", margin: "5px" }}>Quantity:</label>
                            <input type="number" min="1" step="1" defaultValue="1" onChange={this.quantityHandler} onKeyDown="return false"
                                style={{ fontSize: "20px", textAlign: "center", width: "50px" }} />
                        </div>
                        {/* add to cart button */}
                        <div>
                            <button disabled={this.state.buttonStatus} onClick={() => {
                                handleAdd(this.state.title,
                                    this.state.price,
                                    this.state.quantity,
                                    this.state.itemId,
                                    this.state.category)
                            }}
                                style={{ fontSize: "20px", margin: "10px" }} >Add to Cart</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default StorePage