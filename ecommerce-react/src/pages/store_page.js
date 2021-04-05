import React from "react"

const ItemCard = ({ item, index, handleSelect }) => {
    return (
        <div
            id={item.id} name={item.name}
            onClick={() => { handleSelect(item.name, item.id, item.price) }}
            type="button"
        >
            <Card bg="primary" text="white" style={{ height: "200px" }}>
                <Card.Body>
                    <Card.Title style={{ fontSize: "12px" }}>{index}</Card.Title>
                    <Card.Subtitle style={{ fontSize: "16px", textAlign: "center" }}>{item.name}</Card.Subtitle>
                    <Card.Text style={{ fontSize: "16px", textAlign: "center" }}>
                        <br />
                        ${item.price}
                    </Card.Text>
                    <Card.Footer style={{ fontSize: "12px", textAlign: "center" }}>Quantity left: {item.quantity}</Card.Footer>
                </Card.Body>
            </Card>
        </div>
    );
}

function StorePage() {
    return (
        <div id="home_page" className="App-page App-header">
            stoire sotoer
        </div>
    )
}
 
export default StorePage