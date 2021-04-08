import React, {Component} from "react";
import {Button, Col, Table} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

class Report extends Component{

    render() {
        let {purchaseHistory,currency,totalCostOfPurchases , handleCurrencySelect} = this.props
        return (
            <div >
                <Col >
                    <h1 style={{color:"#069"}}> Purchases based on a currency</h1>
                    <hr/>
                    <label htmlFor="date" className="col-3 col-form-label">
                        From: </label>
                    <select value={currency} onChange={handleCurrencySelect}>
                        <option selected value="USD">USD</option>
                        <option value="CAD">CAD</option>
                        <option value="EUR">EUR</option>
                        <option value="GBP">GBP</option>
                        <option value="JPY">JPY</option>
                    </select>
                    <Table className="table table-striped">
                        <thead>
                        <tr>
                            <td>purchaseDate</td>
                            <td>total Cost</td>
                            <td>currency</td>
                            <td>customerName</td>
                            <td>country</td>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            purchaseHistory.filter(history => history.currency.includes(currency))
                                    .map(filteredName => (
                                    <tr key={filteredName.purchaseId}>
                                        <td>{filteredName.purchaseDate }</td>
                                        <td>{totalCostOfPurchases[filteredName.purchaseId]}</td>
                                        <td>{filteredName.currency}</td>
                                        <td>{filteredName.customer.customerName}</td>
                                        <td>{filteredName.customer.address.country}</td>
                                    </tr>
                                    ))
                        }
                        </tbody>
                    </Table>
                </Col>
                <Col >
                    <h1 style={{color:"#069"}}> Purchases in range date</h1>
                    <label htmlFor="date" className="col-3 col-form-label">
                        From: </label>
                    <div className="col-9">
                        <input type="date" name="date"
                               id="date" className="date-picker"/>
                    </div>
                    <label htmlFor="date" className="col-3 col-form-label">
                        To:</label>
                    <div className="col-9">
                        <input type="date" name="date"
                               id="date" className="date-picker"/>
                    </div>
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
                        <tr>
                    {purchaseHistory.filter(history => history.purchaseDate.includes('J'))
                        .map(filteredName => (<td></td>))

                    }
                        </tr>
                        </tbody>
                    </Table>
                </Col>
            </div>
        );
    }
}
export default Report