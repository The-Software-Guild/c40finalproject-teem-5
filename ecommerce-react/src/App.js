import React, { Component } from "react";
import { Switch, Route } from "react-router-dom"
import './App.css'

import NavBar from "./components/nav_bar"
import CheckoutPage from "./pages/checkout_page";
import StorePage from './pages/store_page'
import LoginPage from './pages/login_page'
import PurchaseHistory from "./pages/PurchaseHistory";
import axios from 'axios';
import Report from "./pages/Report";

const STORE_URL = "https://fakestoreapi.com"
const EXCHANGE_RATE_URL = "https://api.ratesapi.io/api"

class App extends Component {
    state = {
        userId: null,
        itemData: [
            {
                title: "test product",
                price: 1.99,
                description: "lorem ipsum set",
                image: "",
                category: "electronic"
            }
        ],
        cartData: [
            {
                title: "test product",
                price: 1.99,
                quantity: 0,
                itemId: 0,
                category: "fake",
                totalForCard: "0.00"
            }
        ],
        exchangeRate: {
            CAD: 1.1234,
            EUR: 1.1234,
            GBP: 1.1234,
            JPY: 1.1234,
            CNY: 1.1234
        },
        LiveExchangeRate:{
            base: 'USD',
            rates: {
                EUR: '',
                CAD: '',
                JPY: '',
                CNY: '',
                GBP: ''
            },
             date: ''
             },
        currentCurrency: "USD",
        customerId: 1,
        addressId: 0,
        totalCost: "0.00",
    }

    constructor(props){
        super(props);
        this.handleClearItems = this.handleClearItems.bind(this);
    }
    
    handleCurrencySelect = (event) => {
        let selected = event.target.value;
        this.setState({ currentCurrency: selected })
        this.handleTotalCalculation();

    }

    handleCustomerSelect = (event) => {
        let selected = event.target.value;
        this.setState({ customerId: selected })
        console.log(selected)
    }

    handleAddressSelect = (event) => {
        let selected = event.target.value;
        this.setState({ addressId: selected })
        console.log(selected)
    }

    handleAddToCart = (itemTitle, itemPrice, itemQuantity, itemId, itemCategory) => {
        var batch = {
            title: itemTitle,
            price: parseFloat(itemPrice.toFixed(2)),
            quantity: parseInt(itemQuantity),
            itemId: itemId,
            category: itemCategory
        }
        this.state.cartData.push(batch);
        alert("Added to Cart!");
        console.log(this.state.cartData);
    }

    handleTotalCalculation = async() =>{
        console.log("test");

        let USDRates = await axios.get(EXCHANGE_RATE_URL + '/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
                    .then(response => response.data.rates);

       this.setState({exchangeRate:USDRates});

       axios.post('http://localhost:8080/cart/findTotal',
       {
           cart:this.state.cartData,
           currency:this.state.currentCurrency,
           exchange:this.state.exchangeRate
       }).then(response =>
                this.setState({cartData:response.data}));
    }

    handleTestAxios = async (event) => {

        let USDRates = await axios.get(EXCHANGE_RATE_URL + '/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY')
            .then(response => response.data.rates)

        this.setState({ exchangeRate: USDRates });

        axios.post('http://localhost:8080/cart/makePurchase',
            {
                purchaseDate: null,
                currency: this.state.currentCurrency,
                exchange: this.state.exchangeRate,
                addressId: this.state.addressId,
                customerId: this.state.customerId,
                cartData: this.state.cartData
            }).then(response =>
                this.setState({ totalCost: parseFloat(response.data.totalCost).toFixed(2) })
            );

        alert("Cart Purchased");
    }

    handleClearItems(){
        const reset = 
        {
            title: null,
            price: null,
            quantity: null,
            itemId: null,
            category: null,
            totalForCard: null
        };
        this.setState({ cartData:reset });
        
    }
    //mount all the data
    componentDidMount() {
        console.log("App is now mounted.")
        this.loadItemData();
        this.loadPurchaseHistory();
        this.loadExchangeRate();
        this.loadPurchaseTotalCost();
        this.state.cartData.pop();

    }
    //retrieve current exchange rate
    loadExchangeRate(){
        axios.get(EXCHANGE_RATE_URL + '/latest?base=USD&symbols=CAD,EUR,GBP,JPY,CNY').then(
            ((res) =>this.setState({LiveExchangeRate:res.data})
        ))
    }
    //retrieve purchase history from database
    loadPurchaseHistory()
    {
        axios.get('http://localhost:8080/cart/history').then((res)=>
            this.setState({purchaseHistory:res.data}))
    }
    //retrieve total cost for each purchase from service layer
    loadPurchaseTotalCost()
    {
        axios.get('http://localhost:8080/cart/totals').then((res)=>
            this.setState({totalCostOfPurchases:res.data}))
    }
    //set items, current exchange rates and purchase history
    loadItemData() {
        this.setState({ loading: true })
        console.log("Loading item data")
        fetch(STORE_URL + "/products")
            .then(data => data.json())
            .then(data => this.setState(
                { itemData: data, loading: false }
            ))
    }

    render() {
        return (
            <div className="App">
                <NavBar />
                <main>
                    <Switch>
                        <Route exact path='/' render={props =>
                        (<LoginPage customer={this.state.customerId} address={this.state.addressId}
                            selectCustomer={this.handleCustomerSelect} selectAddress={this.handleAddressSelect} />)}
                        />
                        <Route path='/store' render={props =>
                        (<StorePage items={this.state.itemData} handleSelect={this.selectHandler}
                            handleAdd={this.handleAddToCart} cart={this.state.cartData} />)}
                        />
                        <Route path='/checkout' render={props =>
                        (<CheckoutPage items={this.state.cartData} currency={this.state.currentCurrency}
                            handleCurrencySelect={this.handleCurrencySelect} handleTestAxios={this.handleTestAxios}
                            totalCost={this.state.totalCost} handleTotalCalculation={this.handleTotalCalculation}
                            exchangeRate = {this.state.LiveExchangeRate} handleClearItems={this.handleClearItems}/>)}

                        />
                        <Route path='/history' render={props =>(<PurchaseHistory
                               // purchaseHistory={this.state.purchaseHistory}
                               // totalCostOfPurchases = {this.state.totalCostOfPurchases}
                               />)}
                        />
                        <Route path='/report' render ={props =>
                            <Report currency ={this.state.currentCurrency}
                                    handleCurrencySelect ={this.handleCurrencySelect}
                            />}/>
                    </Switch>
                </main>
            </div>
        )
    }
}

export default App
