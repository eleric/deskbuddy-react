import React, { Component } from "react";

class AddPhoto extends Component {

    state = {
        photoFile: "",
        msg: "",
        uploaded: false
    };

    handleChangeAdd = (e) => {
        e.preventDefault();
        this.setState({
        photoFile: e.target.files[0]
        })
    };

    handleSubmit = e => {
        e.preventDefault();
        this.setState({
            uploaded: false
        })
        const formData = new FormData();
        console.log(this.state);

        formData.append('file', this.state.photoFile);
        let uploadUrl = '/photos/' + this.props.userName + '/image/' + this.state.photoFile.name;
        let msg = "";
        console.log("Upload Link: " + uploadUrl);
        fetch(uploadUrl, {
            method: 'post',
            body: formData
        }).then(res => {
            if (res.ok) {
                this.setState({
                    uploaded: true
                })
                msg = "File uploaded! - " + this.state.photoFile.name;
                this.setState({
                    msg: msg
                })
                console.log(this.state);
            }
            else {
                console.log("File Failed to upload: " + res);
                msg = "File Failed to upload";
                this.setState({
                    msg: msg
                })
            }
        }).then(()=>{
            console.log("Name: " + this.state.photoFile.name + "Uploaded: " + this.state.uploaded);
            if (this.state.uploaded == true) {
                console.log("HandleAddProps");
                this.props.handleAddProps(this.state.photoFile.name);
            }
            this.setState({
                photoFile: ""
            })
        })
        .catch(error => {
            console.log("File Failed to upload: " + error);
            msg = "File Failed to upload";
            this.setState({
                msg: msg
            })
        });
    }

    render(){
        return(
            <form onSubmit={this.handleSubmit}>
                <div>{this.state.msg}</div>
                <input type="file" onChange={this.handleChangeAdd} name="photoFile" />
                <button>Add</button>
            </form>
        )
        }
}

export default AddPhoto;