export default {
    menu_select(name) {
        var url;
        if ('1-1' == name) {
            url='bid';
        } else if ('1-2' == name) {
            url='bid_document';
        } else if ('1-3' == name) {
            url='supplier';
        } else if ('1-4' == name) {
            url='buyer';
        }
        window.location.href='http://localhost:8000/'+url
    }
}
