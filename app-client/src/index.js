import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import injectTapEventPlugin from 'react-tap-event-plugin'; // Needed for material-ui 

import './pace-theme-flat-top.css';
var pace = require('react-pace/vendor/pace/pace');

injectTapEventPlugin(); // Needed for material-ui 

ReactDOM.render(<App />, document.getElementById('root'));

registerServiceWorker();