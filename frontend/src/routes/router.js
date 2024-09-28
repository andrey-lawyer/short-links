import {createBrowserRouter} from "react-router-dom";
import {ErrorPage} from "../pages/ErrorPage/ErrorPage";
import {HomePage} from "../pages/HomePage/HomePage";
import {StatisticPage} from "../pages/StaticticPage/StatisticPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <HomePage/>
    },
    {
        path: "/l/stats/:shortUrl",
        element: <StatisticPage/>,
    },

    {
        path: "*",
        element: <ErrorPage />,
    },
]
)