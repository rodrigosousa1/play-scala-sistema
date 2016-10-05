angular.module("sistema").directive("uiCnpj", function($filter) {
    return {
        require: "ngModel",
        link: function(scope, element, attrs, ctrl) {
            var _formatCnpj = function(cnpj) {
                cnpj = cnpj.replace(/[^0-9]+/g, '');
                if (cnpj.length > 2)
                    cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2);

                if (cnpj.length > 6)
                    cnpj = cnpj.substring(0, 6) + "." + cnpj.substring(6);

                if (cnpj.length > 10)
                    cnpj = cnpj.substring(0, 10) + "/" + cnpj.substring(10);

                if (cnpj.length > 15)
                    cnpj = cnpj.substring(0, 15) + "-" + cnpj.substring(15, 17);

                return cnpj;
            }

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatCnpj(ctrl.$viewValue));
                ctrl.$render();

            });

            ctrl.$parsers.push(function(value) {
                if (value.length === 18)
                    return value.replace(/[^0-9]+/g, '');
            });

            ctrl.$formatters.push(function(value) {
                if (value)
                    return $filter("cnpj")(value)
            });

        }
    };
});

angular.module("sistema").directive("uiPhone", function($filter) {
    return {
        require: "ngModel",
        link: function(scope, element, attrs, ctrl) {
            var _formatPhone = function(phone) {
                phone = phone.replace(/[^0-9]+/g, '');
                if (phone.length > 2)
                    phone = "(" + phone.substring(0, 2) + ") " + phone.substring(2);

                if (phone.length > 9 && phone[5] !== '9')
                    phone = phone.substring(0, 9) + "-" + phone.substring(9, 13);

                if (phone.length > 10 && phone[5] === '9')
                    phone = phone.substring(0, 10) + "-" + phone.substring(10, 14);

                return phone;
            }

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatPhone(ctrl.$viewValue));
                ctrl.$render();
            });

            ctrl.$parsers.push(function(value) {
                if (value.length === 14 || value.length === 15)
                    return value.replace(/[^0-9]+/g, '');
            });

            ctrl.$formatters.push(function(value) {
                if (value)
                    return $filter("phone")(value)
            });

        }
    };
});

angular.module("sistema").directive("uiCep", function($filter) {
    return {
        require: "ngModel",
        link: function(scope, element, attrs, ctrl) {
            var _formatCep = function(cep) {
                cep = cep.replace(/[^0-9]+/g, '');
                if (cep.length > 2)
                    cep = cep.substring(0, 2) + "." + cep.substring(2);
                if (cep.length > 6)
                    cep = cep.substring(0, 6) + "-" + cep.substring(6, 9);

                return cep;
            }

            element.bind("keyup", function() {
                ctrl.$setViewValue(_formatCep(ctrl.$viewValue));
                ctrl.$render();
            });

            ctrl.$parsers.push(function(value) {
                if (value.length === 10) {
                    return value.replace(/[^0-9]+/g, '');
                }
            });

            ctrl.$formatters.push(function(value) {
                if (value)
                    return $filter("cep")(value)
            });

        }
    };
});
