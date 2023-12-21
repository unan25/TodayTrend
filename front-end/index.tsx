// react
import React from 'react';
import ReactDOM from 'react-dom/client';

// react-router-dom
import { BrowserRouter as Router } from "react-router-dom";

// redux
import { Provider } from 'react-redux';
import { Persistor } from 'redux-persist';
import { store, persistor } from './src/redux/store';

// redux - psersist
import { PersistGate } from 'redux-persist/integration/react';

// react-query
import { QueryClient, QueryClientProvider } from 'react-query';

import './node_modules/bootstrap/dist/css/bootstrap.min.css';
import './global.css';

// App componenent
import App from './src/App';

const rootElement: HTMLElement | null = document.getElementById('root')!;

const root: ReactDOM.Root = ReactDOM.createRoot(rootElement);

const queryClient = new QueryClient();

root.render(
  <Router>
    <QueryClientProvider client={queryClient}>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor as Persistor}>
          <App />
        </PersistGate>
      </Provider>
    </QueryClientProvider>
  </Router>
);
