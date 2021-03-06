import React , {Component} from 'react';
import {Form, Button, Modal, Table} from 'react-bootstrap'
import PurchaseHistoryModal from "./purchaseHistoryModal";


class ItemDetailModal extends Component{

    render() {
    let { showItemsModal,ItemDetail,quantities,handleClose} = this.props;
    return (
        <Modal show={showItemsModal} onHide={handleClose} animation={false} >
            <Modal.Dialog>
                <Modal.Header closeButton>
                    <Modal.Title>Purchased Item's detail</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                        <Table className="table table-striped">
                            <thead>
                            <tr>
                                <td>Item Name</td>
                                <td>Category</td>
                                <td>quantity</td>
                                <td>price</td>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                ItemDetail.map((item, i)=>
                                        <tr key={item.itemId}>
                                            <td>{item.itemName }</td>
                                            <td>{item.category}</td>
                                            <td>{quantities[i]}</td>
                                            <td>{parseFloat(item.price).toFixed(2)}</td>
                                        </tr>)
                            }
                            </tbody>
                        </Table>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>Close</Button>
                </Modal.Footer>
            </Modal.Dialog>
        </Modal>
        )
    }
}
export default ItemDetailModal