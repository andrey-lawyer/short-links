import React, { useEffect, useState } from 'react';
import { Table, Select, Button } from 'antd';
import { ReloadOutlined, HomeOutlined } from '@ant-design/icons';
import { Link } from "react-router-dom";

import { ApiService } from "../../service/api";

import styles from './Statistic.module.css';

const { Option } = Select;

export const Statistics = ({ shortUrl }) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [deviceType, setDeviceType] = useState('');
    const [operatingSystem, setOperatingSystem] = useState('');
    const [page, setPage] = useState(0);
    const [size] = useState(10);

    const fetchStatistics = async () => {
        setLoading(true);
        const result = await ApiService.getStatistics(shortUrl, page, size, deviceType, operatingSystem);
        setData(result);
        setLoading(false);
    };

    useEffect(() => {
        fetchStatistics();
        // eslint-disable-next-line
    }, [shortUrl, deviceType, operatingSystem, page]);

    return (
        <>
            <div className={styles.block}>
            <Link to="/">
                <Button icon={<HomeOutlined />} type="primary" style={{ marginTop: '20px' }}>
                    Return to Home
                </Button>
            </Link>
            <h2 className={styles.title}>
                Statistic for short link{' '}
                <a
                    href={`${process.env.REACT_APP_BACKEND_PATH}/l/${shortUrl}`}
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    {`${process.env.REACT_APP_BACKEND_PATH}/l/${shortUrl}`}
                </a>
            </h2>
            </div>

            <p>Total Clicks: {data.length}</p>

            <div>
                <Select
                    placeholder="Select Device Type"
                    value={deviceType}
                    onChange={(value) => setDeviceType(value)}
                    style={{ width: 200, marginRight: 8 }}
                >
                    <Option value="">All</Option>
                    <Option value="Mobile">Mobile</Option>
                    <Option value="PC">PC</Option>
                </Select>

                <Select
                    placeholder="Select Operating System"
                    value={operatingSystem}
                    onChange={(value) => setOperatingSystem(value)}
                    style={{ width: 200, marginRight: 8 }}
                >
                    <Option value="">All</Option>
                    <Option value="Windows">Windows</Option>
                    <Option value="MacOS">MacOS</Option>
                    <Option value="Linux">Linux</Option>
                    <Option value="Unknown">Unknown</Option>
                </Select>

                <Button onClick={fetchStatistics} icon={<ReloadOutlined />} />
            </div>

            <Table
                className={styles.table}
                dataSource={data?.records}
                loading={loading}
                rowKey="id"
                pagination={{
                    current: page + 1,
                    pageSize: size,
                    total: data?.totalCount,
                    onChange: (newPage) => {
                        setPage(newPage - 1);
                    },
                }}
                columns={[
                    {
                        title: 'Clicked At',
                        dataIndex: 'clickedAt',
                        key: 'clickedAt',
                        render: (text) => new Date(text).toLocaleString(),
                    },
                    {
                        title: 'IP Address',
                        dataIndex: 'ipAddress',
                        key: 'ipAddress',
                    },
                    {
                        title: 'User Agent',
                        dataIndex: 'userAgent',
                        key: 'userAgent',
                    },
                    {
                        title: 'Operating System',
                        dataIndex: 'operatingSystem',
                        key: 'operatingSystem',
                    },
                    {
                        title: 'Device Type',
                        dataIndex: 'deviceType',
                        key: 'deviceType',
                    },
                    {
                        title: 'Country',
                        dataIndex: 'country',
                        key: 'country',
                    },
                    {
                        title: 'City',
                        dataIndex: 'city',
                        key: 'city',
                    },
                ]}
            />


        </>
    );
};

