import { Typography } from "antd";
import styles from "./MainInfo.module.css";

const {Paragraph, Link } = Typography;

export const MainInfo = () =>
    <div className={styles.wrapper} >
    <Paragraph>
        Technologies used: Java, PostgreSQL, ReactJS, Docker, GitHub
    </Paragraph>
    <Paragraph>Developed by Andrey Uvarov</Paragraph>
    <Link href="https://github.com/andrey-lawyer" target="_blank">
        GitHub: github.com/andrey-lawyer
    </Link>
</div>