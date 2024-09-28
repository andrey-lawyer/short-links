export const validateURL = (_, value) => {
    const urlPattern = /^(https?|ftp|file):\/\/.+$/; // Используем то же регулярное выражение, что и на бэкенде

    if (!value || urlPattern.test(value)) {
        return Promise.resolve();
    } else {
        return Promise.reject(new Error("Please enter a valid URL!"));
    }
};