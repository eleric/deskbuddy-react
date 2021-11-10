import React, { Component } from "react";

class AddPhoto extends Component {

    state = {
        photoName: ""
    };

    handleChangeAdd = (e) => { 
        this.setState({
        [e.target.name]: e.target.value
        })
    };

    handleSubmit = e => {
        e.preventDefault();
        this.props.handleAddProps(this.state.photoName);
        this.setState({
            photoName: ""
        })
    }

    render(){
        return(
            <form onSubmit={this.handleSubmit}>
                <input type="text" value={this.state.photoName} onChange={this.handleChangeAdd} name="photoName" />
                <button>Add</button>
            </form>
        )
        }
}

export default AddPhoto;