import React from "react";
import PhotoItem from "./PhotoItem"

class ListPhotos extends React.Component {

    render(){
        return(
            <ul>
                {this.props.photos.map(p => (
                    <PhotoItem photo={p} handleChangeProps={this.props.handleChangeProps} />
                ))}
                <button onClick={() => this.props.handleDeleteProps(this.props.photos)}>
                    Delete
                </button>
            </ul>

        )
        }
}

export default ListPhotos;