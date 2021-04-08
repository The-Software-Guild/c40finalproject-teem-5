import React, {Component} from "react";
import {Row, Col, Table} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

class Report extends Component{

     min = Date.parse('2001-10-01');
     max = Date.parse('2022-01-01');
        fromDate='';
        toDate='';

     //set the selected date
    DateHandlerFrom = (f) => {
        this.fromDate=f;
    };

    DateHandlerTo = (t) => {
        this.toDate=t;
    };



    render() {

        let {purchaseHistory,currency,totalCostOfPurchases , handleCurrencySelect} = this.props
        return (
            <div >
                <Row className="justify-content-md-center">
                <Col  className="justify-content-md-center  border border-dark">
                    <h1 style={{color:"#069"}}> Purchases based on a currency</h1>
                    <Row className="justify-content-md-center">
                    <label htmlFor="date" className="col-3 col-form-label">
                        Currency: </label>
                    <select value={currency} onChange={handleCurrencySelect}>
                        <option selected value="USD">USD</option>
                        <option value="CAD">CAD</option>
                        <option value="EUR">EUR</option>
                        <option value="GBP">GBP</option>
                        <option value="JPY">JPY</option>
                    </select>
                    </Row>
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
                <Col className="justify-content-md-center  border border-dark">
                    <h1 style={{color:"#069"}}> Purchases in range date</h1>
                    <Row className="justify-content-md-center">
                        <Col>
                            <label htmlFor="date" >
                                From: </label>
                            <div >
                                <input type="date" name="date"
                                       id="from" className="date-picker" onChange={(e)=> this.DateHandlerFrom(e.target.value)}/>
                            </div>
                        </Col>
                        <Col>
                            <label htmlFor="date" >
                                To:</label>
                            <div>
                                <input type="date" name="date"
                                       id="to" className="date-picker" onChange={(e)=> this.DateHandlerTo(e.target.value)}/>
                            </div>
                        </Col>
                    </Row>
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
                            purchaseHistory.filter(history => (history.purchaseDate>this.fromDate) && (history.purchaseDate<this.toDate))
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
                </Row>
            </div>
        );
    }
}
export default Report