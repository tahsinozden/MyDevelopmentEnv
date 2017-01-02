'use strict';

describe('Controller: DeleteVotetableCtrl', function () {

  // load the controller's module
  beforeEach(module('mainApp'));

  var DeleteVotetableCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DeleteVotetableCtrl = $controller('DeleteVotetableCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DeleteVotetableCtrl.awesomeThings.length).toBe(3);
  });
});
