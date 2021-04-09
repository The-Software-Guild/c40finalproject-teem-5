import React, {Component} from "react";
import {Card, Button, Container, Row, Col, Table} from 'react-bootstrap';
import PurchaseHistoryModal from "./purchaseHistoryModal";
import 'bootstrap/dist/css/bootstrap.min.css';

class PurchaseHistory extends Component{

    state ={
        showDetailModal: false,
        purchaseHistoryDetail:
            {
                purchaseId: '',
                purchaseDate: '',
                currency: '',
                exchange: {
                    exchangeId: '',
                    cad: '',
                    eur: '',
                    gbp: '',
                    jpy: '',
                    cny:''
                },
                customer: {
                    customerId: '',
                    customerName: '',
                    customerEmail: '',
                    customerPhone: '',
                    address: {
                        addressId: '',
                        street: '',
                        city: '',
                        state: '',
                        postal: '',
                        country: ''
                    }
                },
                items: [{
                    itemId:'',
                    itemName:'',
                    category:'',
                    price:'',
                }],
                quantities: [{}]

            }
    }

    handlePurchaseHistoryModalClose = (event) => {
        console.log("Closing Detail Modal")
        this.setState({ showDetailModal : false})
    }

    handleDetailPurchaseHistoryModalOpen = (value1) => {
        console.log("Opening purchase history detail Modal")
        // if (event) event.preventDefault();
        // let purchaseId = value1.purchaseId;
        console.log(`showing detail of purchase Id ${value1.purchaseId}`)
        this.setState({ showDetailModal : true})
        this.setState({ purchaseHistoryDetail: value1})
        // console.log(this.state.showDetailModal)
    }

    render() {
        let{purchaseHistory , totalCostOfPurchases} = this.props
        return (
            <div >
                <h1 style={{color:"#069"}}> List of Purchases</h1>
                {/*<Table style={{alignContent:"center",  textAlign: "center"*/}
                {/*    , borderColor:"red",width:"80%",  border: "1px solid black"*/}
                {/*}}>*/}
                <Table className="table table-striped">
                    <thead>
                    <tr>
                        <td>purchaseDate</td>
                        <td>total Cost</td>
                        <td>currency</td>
                        <td>customerName</td>
                        <td>country</td>
                        <td>Purchase Detail</td>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        purchaseHistory.map(
                            history =>
                                <tr key={history.purchaseId}>
                                    <td>{history.purchaseDate }</td>
                                    <td>{parseFloat(totalCostOfPurchases[history.purchaseId]).toFixed(2)}</td>
                                    <td>{history.currency}</td>
                                    <td>{history.customer.customerName}</td>
                                    <td>{history.customer.address.country}</td>
                                    <td><Button onClick={(e) => {this.handleDetailPurchaseHistoryModalOpen(history)}}>Purchase Detail</Button></td>
                                </tr>)
                    }
                    </tbody>
                </Table>
                <PurchaseHistoryModal
                    showDetailModal={this.state.showDetailModal}
                    handleClose={this.handlePurchaseHistoryModalClose}
                    purchaseHistory={this.state.purchaseHistoryDetail} />
            </div>
        );
    }
}
export default PurchaseHistory