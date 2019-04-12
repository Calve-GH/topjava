function makeEditable(ctx) {
    context = ctx;
    filterForm = $('#filterForm');
    form = $('#detailsForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow($(this).attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: context.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable();
        successNoty("Deleted");
    });
}

function updateTable() {
    $.get(context.ajaxUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function resetFilter() {
    $(".reset").closest('form').find("input[type=date], textarea").val("");
    $(".reset").closest('form').find("input[type=time], textarea").val("");
    updateTable();
}

function save() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        if ($("#startDate").val() != null || $("#endDate").val() != null)
            updateTableFiltered();
        else
            updateTable();
        successNoty("Saved");
    });
}

function updateTableFiltered() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrlFilter,
        data: filterForm.serialize()
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    })
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 2000
    }).show();
}

function enable(chkbox) {
    var enabled = chkbox.is(":checked");
    chkbox.parent().parent().css("text-decoration", enabled ? "none" : "line-through");
    $.ajax({
        url: ajaxUrl + chkbox.attr('id1'),
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            successNoty(enabled ? 'Enabled' : 'Disabled');
        }
    })
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}

$(".userState").onchange(function () {
    alert("hihi");
});

$(".userState").change(function () {
    alert("hehe");
    if ($(this).is(':checked')) {
        alert('checked');
    } else
        alert('unchecked')
});