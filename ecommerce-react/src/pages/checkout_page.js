import React from 'react'
import { Button } from 'react-bootstrap'


const ListedItem = ({item, key }) =>{

    return(
        <span>test text s
        <Button style={{backgroundColor: "lightblue"}} className="col-3 btn btn-danger">
            <div>
                {item.title}
                <br/>
                {item.price}
                <br/>
                {item.quantity}
                <br/>
                <br/>
                <br/>
            </div>
        </Button>
        </span>
    );
}

class CheckoutPage extends React.Component {



  render(){
      let{items} = this.props
      return (

        <div id="checkout_page" className="App-page">
            {items.map((item, i) => {
                return <ListedItem item={item} key={i} />
            })}
        </div>
    )
  }
}

export default CheckoutPage