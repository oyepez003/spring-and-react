import React from 'react';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Paper from 'material-ui/Paper';
import Divider from 'material-ui/Divider';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import Badge from 'material-ui/Badge';

import axios from 'axios';
require('react-bootstrap-table/dist/react-bootstrap-table-all.min.css');

/**
 * ApiViewer component to get the Api data and save the request history.
 */
class ApiViewer extends React.Component {

  constructor(props) {
    super(props);
    this.state = {history: [], data: [], isReget: false, showHistory: false};
    this.handleGetApiData = this.handleGetApiData.bind(this);
    this.handleDeleteHistory = this.handleDeleteHistory.bind(this);
    this.getHistory(this);
  }
  
  /**
   * Button formatter for the action column.
   */
  historyButtonFormatter = (cell, row) => 
    <div className="text-center">
        <RaisedButton onClick={() => this.handleReGetHistory(row)} className="history-button" primary={true} label="Get" />
        <RaisedButton onClick={() => this.handleDeleteHistory(row.id)} secondary={true} label="Delete" />
    </div>

  /**
   * Getting the history request of the API
   */
  handleGetApiData(evt, params){
    if(evt !== undefined){
      params = Array.from(evt.target.elements)
            .filter(el => el.name)
            .reduce((a, b) => ({...a, [b.name]: b.value}), {});
    
      if(this.state.select !== undefined){
        params[Object.keys(this.props.selects)[0]] = this.state.select;
      }

      params["limit"] = 15;

      evt.preventDefault();
    }
    
    this.cleanObject(params);

    axios.get(this.props.endpoint, {params})
      .then(res => {
        let result = this.props.resultsIndex !== undefined ? res.data[this.props.resultsIndex] : res.data;

        if(result !== undefined){
          this.setState({data: result.slice(0,14)});
          
          if(! this.state.isReget){
            this.newHistory(params);
          }

          this.setState({isReget: false});
        }
      })
      .catch(err => {
        console.error(err);
    });

    return false;
  }

  /**
   * Re Get the History request
   * @param {*} params 
   */
  handleReGetHistory(row){
    this.setState({isReget: true, showHistory : false});
    this.handleGetApiData(undefined, row.params);
  }

  /**
   * Save the History request
   * @param {*} params 
   */
  newHistory(params){
    axios.post("http://localhost:8080/history", {
      api: 'test',
      params,
    })
      .then(res => {
        this.getHistory(this);
      })
      .catch(err => {
        console.error(err);
    });
  }

  /**
   * Delete the History request
   * @param {*} params 
   */
  handleDeleteHistory(id){
    axios.delete("http://localhost:8080/history/" + id)
      .then(res => {
        this.getHistory(this);
      })
      .catch(err => {
        console.error(err);
    });
  }

  /**
   * Clean object attribute with null, undefined or "" values
   * @param {*} obj 
   */
  cleanObject(obj) {
    for (var propName in obj) { 
      if (obj[propName] === null || obj[propName] === undefined || obj[propName] === "") {
        delete obj[propName];
      }
    }
  }

  /**
   * Getting the history request of the API
   */
  getHistory(_this){
    axios.get('http://localhost:8080/history/test')
      .then(function(res){
        if(res.data !== undefined){
          _this.setState({history: res.data});
        }
      })
      .catch(err => {
        console.error(err);
      });
  }

  /**
   * On change a select in the Api search form
   */
  handleChangeSelect = (event, index, value) => {
    this.setState({select: value});
  };

  /**
   * Render method 
   */
  render() {
    const jsx = (
      <div className="container">
        <p>
            <br/>
            <b>Resource:</b> <a target="_new" href={this.props.resource}>{this.props.resource}</a>
        </p>

        <FlatButton onClick={() => this.setState({showHistory : false})} label="Search" primary={true} />
        <Badge
          badgeContent={this.state.history.length}
          className="history-badge"
          secondary={true}>
          <FlatButton onClick={() => this.setState({showHistory : true})} label="History" secondary={true} />
        </Badge>
        
        {! this.state.showHistory ? (
          <div> 
            <form className="form-search" onSubmit={this.handleGetApiData}>
            <Paper zDepth={2}>
                {Object.keys(this.props.cols).map(function (key, i) {
                    let jsx; 
                    if(this.props.selects !== undefined && this.props.selects[key] !== undefined){
                      
                      jsx = (
                        <div key={key}>
                          <SelectField name={key} labelStyle={{height:43}} iconStyle={{marginRight:20}} hintText={this.props.cols[key]} className="history-form-select-field" value={this.state.select} onChange={this.handleChangeSelect}>
                            <MenuItem value="" primaryText="" />
                            {Object.keys(this.props.selects[key]).map((sKey, y) =>
                              <MenuItem key={y} value={this.props.selects[key][sKey]} primaryText={this.props.selects[key][sKey]} />
                            , this)}
                          </SelectField>
                          <Divider />
                        </div>
                      )

                    } else{
                      jsx = (
                        <div key={key}>
                            <TextField name={key} hintText={this.props.cols[key]} className="history-form-text-field" underlineShow={false} />
                            <Divider />
                        </div>
                      )
                    }
                    
                    return jsx
                }, this)}
                <RaisedButton type="submit" className="history-button" primary={true} label="Get" />
            </Paper>
          </form>

          <br/>
          <BootstrapTable pagination data={this.state.data} striped hover condensed>
          {Object.keys(this.props.cols).map((key, i) =>
              <TableHeaderColumn dataSort filter={ { type: 'TextFilter'} }  isKey={i===0} key={key} dataField={key}>{this.props.cols[key]}</TableHeaderColumn>
          , this)}
          </BootstrapTable> 
          </div> 
        ) : (
          <div style={{textAlign:'right'}}>
          <RaisedButton onClick={() => this.handleDeleteHistory('all')} secondary={true} label="Delete All" />
          <br/>
          <br/>
          <BootstrapTable data={this.state.history} striped hover condensed>
              <TableHeaderColumn isKey dataField='id'>ID</TableHeaderColumn>
              <TableHeaderColumn dataField='date'>Date</TableHeaderColumn>
              <TableHeaderColumn dataField='params'>Params</TableHeaderColumn>
              <TableHeaderColumn dataField='actions' dataFormat={this.historyButtonFormatter}>Actions</TableHeaderColumn>
          </BootstrapTable>  
          </div>
        )}

      </div>
    );

    return jsx;
  }
}
 
// export the component
export default ApiViewer;