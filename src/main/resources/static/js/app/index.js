var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            var author = $('#author').val();
            var userNames = $('#name').text();
            if(userNames==author){
                _this.update();
            }else{
                alert(author+userNames);
                alert('작성자만이 수정 가능합니다');
            }

        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
     update : function () {
            var data = {
                title: $('#title').val(),
                userNames : $('#userNames').val(),
                content: $('#content').val()
            };

            var id = $('#id').val();

            $.ajax({
                type: 'PUT',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)

            }).done(function() {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },
     delete : function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'

        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
     }

};

main.init();
