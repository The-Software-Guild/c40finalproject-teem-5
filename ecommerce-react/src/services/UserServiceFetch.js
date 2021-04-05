import axios from 'axios';

class UserServiceFetch{
    addPurchase(){
        return axios.post('http://localhost:8080/cart/makePurchase',{
            purchaseID:75,
            purchaseDate:null,
            currency:"USD",
            exchange:null,
            customer:null,
            items:null
        }).then(response => response.json)
    }

    getCurrentExchange(exchangeRate){
        return axios.get('http://data.fixer.io/api/latest?access_key=5577025857c2f2cc601f6bd524482428')
            .then(response => response.data.rates);



    }
}

export default new UserServiceFetch();