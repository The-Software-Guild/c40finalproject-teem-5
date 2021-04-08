import React , {Component} from 'react';
import { Form, Button, Modal } from 'react-bootstrap'
import ItemDetailModal from "./ItemDetailModal";


class PurchaseHistoryModal extends Component{

    state = {
        showItemsModal: false,
        ItemDetail:
            [{
                itemId: '',
                itemName: '',
                category: '',
                price: ''
            }],
            quantities:
                [{}]
        }


    handleItemModalClose = (event) => {
        console.log("Closing item detail Modal")
        this.setState({ showItemsModal : false})
    }

    handleItemModalOpen = (value1) => {
        console.log("Opening items detail Modal")
        // if (event) event.preventDefault();
        // let purchaseId = value1.purchaseId;
        console.log(`showing detail of purchase Id ${value1.purchaseId}`)
        this.setState({ showItemsModal : true})
        this.setState({ ItemDetail: value1.items})
        this.setState({ quantities: value1.quantities})
        // console.log(this.state.showDetailModal)
    }


    render() {
    let { showDetailModal,  handleClose, purchaseHistory} = this.props;
    return (
        <Modal show={showDetailModal} onHide={handleClose} animation={false}>
            <Modal.Dialog>
                <Modal.Header closeButton>
                    <Modal.Title>Purchase Detail for {purchaseHistory.customer.customerName}</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Form>
                        <Form.Group controlId="country">
                            <Form.Label>Country: </Form.Label>
                            <Form.Control type="text" readOnly name="Country"
                                          value={purchaseHistory.customer.address.country} />
                        </Form.Group>
                        <Form.Group controlId="state">
                            <Form.Label>State: </Form.Label>
                            <Form.Control type="text" readOnly name="State"
                                          value={purchaseHistory.customer.address.state} />
                        </Form.Group>
                        <Form.Group controlId="city">
                            <Form.Label>City: </Form.Label>
                            <Form.Control type="text" readOnly name="company"
                                          value={purchaseHistory.customer.address.city} />
                        </Form.Group>
                        <Form.Group controlId="street">
                            <Form.Label>Street: </Form.Label>
                            <Form.Control type="text" readOnly name="street"
                                          value={purchaseHistory.customer.address.street} />
                        </Form.Group>
                        <Form.Group controlId="postal">
                            <Form.Label>Postal Code: </Form.Label>
                            <Form.Control type="text" readOnly name="phone"
                                          value={purchaseHistory.customer.address.postal} />
                        </Form.Group>
                        <Form.Group controlId="customerEmail">
                            <Form.Label>Email:</Form.Label>
                            <Form.Control type="text" readOnly name="email"
                                          value={purchaseHistory.customer.customerEmail} />
                        </Form.Group>
                        <Form.Group controlId="phone">
                            <Form.Label>Phone:</Form.Label>
                            <Form.Control type="text" readOnly name="phone"
                                          value={purchaseHistory.customer.customerPhone} />
                        </Form.Group>
                    </Form>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                    <Button variant="secondary" onClick={(e)=>
                                               {this.handleItemModalOpen(purchaseHistory)}}>Item Detail</Button>
                </Modal.Footer>
                <ItemDetailModal showItemsModal ={this.state.showItemsModal}
                                 ItemDetail ={this.state.ItemDetail}
                                 quantities = {this.state.quantities}
                                 handleClose={this.handleItemModalClose}/>
            </Modal.Dialog>
        </Modal>
        )
    }
}
export default PurchaseHistoryModal