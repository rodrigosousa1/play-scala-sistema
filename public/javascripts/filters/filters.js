angular.module("sistema").filter("cnpj", function() {
    return function(input) {
        var str = input + '';
        str = str.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d)/, '$1.$2.$3/$4-$5');
        return str;
    }
});

angular.module("sistema").filter("phone", function() {
    return function(input) {
        var str = input + '';
        if (str.length === 11)
            str = str.replace(/^(\d{2})(\d{5})(\d)/, '($1) $2-$3');
        else
            str = str.replace(/^(\d{2})(\d{4})(\d)/, '($1) $2-$3');

        return str;
    }
});

angular.module("sistema").filter("cep", function() {
    return function(input) {
        var str = input + '';
        str = str.replace(/^(\d{2})(\d{3})(\d)/, '$1.$2-$3');
        return str;
    }
});