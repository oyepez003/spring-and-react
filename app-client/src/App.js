import React, { Component } from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';
import FontIcon from 'material-ui/FontIcon';
import ApiViewer from './components/ApiViewer'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import './App.css';

/**
 * App. Main component.
 */
class App extends Component {

  render() {
    return (
      <MuiThemeProvider>
        <div className="App">            
          <Tabs>
              <Tab
                icon={<FontIcon className="material-icons">Universities</FontIcon>}
                label="hipolabs"
              >
                <ApiViewer  resource="http://universities.hipolabs.com" 
                            endpoint="http://universities.hipolabs.com/search" 
                            cols={{
                              "alpha_two_code": "Alpha Codes", 
                              "name":"Name", 
                              "country": "Country", 
                              "web_page": "Web Page", 
                              "domain":"Domain"
                            }}
                            selects={{
                              "country": ["United States","Colombia"], 
                            }}  
                />
              </Tab>
              <Tab
                icon={<FontIcon className="material-icons">Patent Docs</FontIcon>}
                label="uspto"
              >
                <ApiViewer  resource="https://ptabdata.uspto.gov/ptab-api/swagger/index.html"
                            endpoint="https://ptabdata.uspto.gov/ptab-api/documents" 
                            cols={{
                              "id": "Id", 
                              "title":"Title", 
                              "documentNumber": "Document #", 
                              "type": "Type", 
                              "trialNumber":"Trial #"
                            }} 
                            selects={{
                              "type": ["notice", "Mandatory Notice", "motion", "opposition", "power of attorney"], 
                            }} 
                            resultsIndex="results"
                />
              </Tab>
            </Tabs>
        </div>
      </MuiThemeProvider>
    );
  }
}

// Export the Main component.
export default App;