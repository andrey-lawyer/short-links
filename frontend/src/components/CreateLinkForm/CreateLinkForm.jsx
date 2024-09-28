import React, { useState } from "react";
import { Form, Input, Button, Tooltip, message } from 'antd';
import { CopyOutlined, InfoCircleOutlined} from '@ant-design/icons';
import { Link } from "react-router-dom";

import styles from './CreateLinkForm.module.css';
import {validateURL} from "../../util/validation";
import {ApiService} from "../../service/api";

export const CreateLinkForm = () => {
    const [longLink, setLongLink] = useState("");
    const [shortLink, setShortLink] = useState("");

    const handleSubmit = async (values) => {
        try {
            const result = await ApiService.createShortLink({ originalUrl: values.longLink });
            setShortLink(result.shortUrl);
            message.success("Link successfully created!");
        } catch (err) {
            message.error("Error creating short link'");
        }
    };

    const shortLinkFull = shortLink ? `${process.env.REACT_APP_BACKEND_PATH}/l/${shortLink}` : "";
    const staticLink = shortLink ? `/l/stats/${shortLink}` : "";

    const [tooltipText, setTooltipText] = useState('Copy');

    const handleCopy = (text) => {
        navigator.clipboard.writeText(text).then(() => {
            setTooltipText('Copied!');
            setTimeout(() => setTooltipText('Copy'), 2000); // Возвращаем тултип через 2 секунды
        });
    };

    return (
        <Form
            className={styles.form}
            layout="vertical"
            onFinish={handleSubmit}
        >
            <Form.Item
                label="For create link, enter your long link:"
                name="longLink"
                rules={[
                    { required: true, message: "Please enter a URL!" },
                    { validator: validateURL },
                    { min: 5, message: "URL must be at least 5 characters long!" },
                    { max: 2048, message: "URL must be no more than 2048 characters long!" }
                ]}
            >
                <Input
                    placeholder="Enter your long link here"
                    value={longLink}
                    onChange={(e) => setLongLink(e.target.value)}
                />
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit" block>
                    CREATE LINK!
                </Button>
            </Form.Item>

            {shortLink && (
                <>
                    <Form.Item label="Your short link:">
                        <Input
                            value={shortLinkFull}
                            suffix={
                                <Tooltip title={tooltipText}>
                                    <CopyOutlined
                                        onClick={() => handleCopy(shortLinkFull)}
                                        style={{ cursor: 'pointer', color: '#1890ff' }}
                                    />
                                </Tooltip>
                            }
                            readOnly
                        />
                    </Form.Item>

                    <Form.Item >
                        <div className={styles.block}>
                            <Tooltip title="View detailed statistics for your short link">
                                <InfoCircleOutlined style={{ fontSize: '24px', color: '#1890ff', marginRight: '10px' }} />
                            </Tooltip>
                                <p>You can view the detailed statistics by clicking this:   <Link to={staticLink} >
                                    Go to Statistics
                                </Link></p>

                        </div>
                    </Form.Item>
                </>
            )}
        </Form>
    );
};
