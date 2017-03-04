interface thisiswhatitis {
    set?: (
    onsucess: (response: string) => void,
    onfail: (response: string) => void,
    key: string,
    value: string
    ) => void;

    get?: (
    onsucess: (response: string) => void,
    onfail: (response: string) => void,
    key: string
    ) => void;

    delete?: (
    onsucess: (response: string) => void,
    onfail: (response: string) => void,
    key: string
    ) => void;
}
interface CordovaPlugins {
    thisiswhatitis: thisiswhatitis
}

