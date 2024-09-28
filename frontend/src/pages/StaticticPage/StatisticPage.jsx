import { useParams } from 'react-router-dom';
import {Statistics} from "../../components/Statistics/Statistics";

export const StatisticPage = () => {
    const { shortUrl } = useParams();

   return <Statistics shortUrl={shortUrl} />

};

