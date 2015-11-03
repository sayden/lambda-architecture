var redis = require("redis"),
    client = redis.createClient();


module.exports = function(io){
    setInterval(function(){
        client.zrange("countries", '0', '-1', function(err, keys){
            keys.map(function(key){
                client.zscore('countries', key, function(err, score){
                    var json = {}; json[key] = score;
                    io.emit('update', json);
                });
            })
        });
    }, 1000);
};
