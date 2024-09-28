
import styles from './App.module.css';
import { RouterProvider } from "react-router-dom";
import {router} from "./routes/router";


function App() {
    return (
<>
    <header className={styles.header}> Service for creating short links </header>
    <main className={styles.container}>
        <RouterProvider router={router}/>
    </main>
</>
);
}

export default App;
