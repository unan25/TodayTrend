// react
import React from 'react';
import ReactDOM from 'react-dom/client';

// redux
import { Provider } from "react-redux";
import store from "./src/redux/store";


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
  <QueryClientProvider client={queryClient}>
    <Provider store={store}>
      <App />
    </Provider>
  </QueryClientProvider>
);
