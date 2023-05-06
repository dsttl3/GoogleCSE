package util;

public class GetIndex {

    public static String load() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "\n" +
                "<head>\n" +
                "    <title>dsttl3的Google搜索</title>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <link rel=\"apple-touch-icon\" href=\"ico/icon.png\">\n" +
                "    <style>\n" +
                "        body,\n" +
                "        ul,\n" +
                "        li,\n" +
                "        div,\n" +
                "        a,\n" +
                "        p,\n" +
                "        input,\n" +
                "        bottom,\n" +
                "        samp {\n" +
                "            padding: 0;\n" +
                "            margin: 0;\n" +
                "            background: none;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            background: url(https://api.vvhan.com/api/bing?type=sj) no-repeat center center;\n" +
                "            background-size: cover;\n" +
                "            background-attachment: fixed;\n" +
                "        }\n" +
                "\n" +
                "        .main {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "\n" +
                "        .main_center {\n" +
                "            width: 100%;\n" +
                "            position: fixed;\n" +
                "            top: 18%;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .search-box {\n" +
                "            background: #ffffffdd;\n" +
                "            width: 450px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 32px;\n" +
                "            border-radius: 32px;\n" +
                "        }\n" +
                "\n" +
                "        #search-input {\n" +
                "            width: 380px;\n" +
                "            padding: 6px 32px;\n" +
                "            font-size: 22px;\n" +
                "            border: none;\n" +
                "            outline: none;\n" +
                "            border-style: solid;\n" +
                "            border-width: 1px;\n" +
                "            border-radius: 32px;\n" +
                "            background: #ffffffdd;\n" +
                "        }\n" +
                "\n" +
                "        .sub_btn {\n" +
                "            width: 200px;\n" +
                "            padding: 6px;\n" +
                "            margin-top: 32px;\n" +
                "            font-size: 22px;\n" +
                "            border: none;\n" +
                "            outline: none;\n" +
                "            border-style: solid;\n" +
                "            border-width: 1px;\n" +
                "            border-radius: 8px;\n" +
                "            background: #66ccffdd;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        .search-box ul {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            list-style: none;\n" +
                "            position: absolute;\n" +
                "            background-color: #ffffffdd;\n" +
                "            border: 1px solid #ccc;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 1px 1px 5px #ccc;\n" +
                "            z-index: 2;\n" +
                "            /* 添加z-index属性 */\n" +
                "            top: 100%;\n" +
                "            /* 将下拉菜单置于搜索框下方 */\n" +
                "            width: 450px;\n" +
                "        }\n" +
                "\n" +
                "        .search-box li {\n" +
                "            padding: 5px;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "\n" +
                "        .search-box li:hover {\n" +
                "            background-color: #ccc;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"main\">\n" +
                "\n" +
                "        <div class=\"main_center\">\n" +
                "            <div class=\"search-box\">\n" +
                "                <form action=\"https://google.dsttl3.cn/\">\n" +
                "                    <img src=\"https://wb.dsttl3.cn/img/googlelogo.png\" />\n" +
                "                    <input id=\"search-input\" type=\"text\" name=\"sou\" placeholder=\"\" autofocus autocomplete=\"off\" />\n" +
                "                    <ul id=\"search-results\"></ul>\n" +
                "                    <button class=\"sub_btn\" type=\"submit\">搜索</button>\n" +
                "\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <script>\n" +
                "        const searchInput = document.getElementById('search-input');\n" +
                "        const searchResults = document.getElementById('search-results');\n" +
                "        searchInput.addEventListener('input', (e) => {\n" +
                "            const searchTerm = e.target.value.trim();\n" +
                "            if (searchTerm === '') {\n" +
                "                searchResults.innerHTML = '';\n" +
                "                return;\n" +
                "            }\n" +
                "            fetch(`https://google.dsttl3.cn/Sugrec?wd=${searchTerm}`)\n" +
                "                .then(response => response.json())\n" +
                "                .then(data => {\n" +
                "                    const suggestions = data.g;\n" +
                "                    const resultsHTML = suggestions.map(suggestion => `<li>${suggestion.q}</li>`).join('');\n" +
                "                    searchResults.innerHTML = resultsHTML;\n" +
                "                    // 点击提示词替换输入框内容\n" +
                "                    searchResults.querySelectorAll('li').forEach(item => {\n" +
                "                        item.addEventListener('click', () => {\n" +
                "                            searchInput.value = item.textContent;\n" +
                "                            searchResults.innerHTML = '';\n" +
                "                        })\n" +
                "                    })\n" +
                "                })\n" +
                "                .catch(error => {\n" +
                "                    console.error(error);\n" +
                "                });\n" +
                "        });\n" +
                "        // 点击页面其他位置隐藏下拉菜单\n" +
                "        document.addEventListener('click', (e) => {\n" +
                "            if (e.target !== searchInput && e.target !== searchResults) {\n" +
                "                searchResults.innerHTML = '';\n" +
                "            }\n" +
                "        });\n" +
                "    </script>\n" +
                "</body>";
    }
}
