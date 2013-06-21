﻿/*
* jQuery Custom Scrollbar Script Oct 20th
* Visit http://www.dynamicdrive.com/ for full source code
*/

/// <reference path="jquery-1.6.2.js" />

(function ($) {

    $.fn.slickscroll = function (options) {

        var scrollcontainer;
        var scrollcontent;
        var scrollbar
        var scrollcontentpos = new Array(0, 0);
        var scrollcontainerpos = new Array(0, 0);
        var mousewheelscrolltop;
        var ap = false;
        var scrollhw = 0;

        //public methods

        this.InValidate = function () {
            scrollhw = (options.verticalscrollbar ? scrollcontent.prop('scrollHeight') : scrollcontent.prop('scrollWidth'));
            $(window).resize();
        }

        this.scrollBy = function (n, speed) {
            if (options.verticalscrollbar) {
                var scrolltop = scrollcontent.scrollTop() + n;
                if (scrolltop < 0) return;
                scrollcontent.animate({ scrollTop: scrolltop + 'px' }, speed);
                scrollbar.css({ "top": scrollcontainerpos.top + scrolltop / (scrollcontent.prop('scrollHeight') / scrollcontent.height()) + "px" });
            }
            else {
                var scrollleft = scrollcontent.scrollLeft() + n;
                if (scrollleft < 0) return;
                scrollcontent.animate({ scrollLeft: scrollleft + 'px' }, speed);
                scrollbar.css({ "left": scrollcontainerpos.left + scrollleft / (scrollcontent.prop('scrollWidth') / scrollcontent.width()) + "px" });
            }
        };

        this.scrollTop = function (speed) {
            if (options.verticalscrollbar) {
                scrollcontent.animate({ scrollTop: '0px' }, speed);
                scrollbar.animate({ top: scrollcontainerpos.top + 'px' }, speed);
            }
            else {
                scrollcontent.animate({ scrollLeft: '0px' }, speed);
                scrollbar.animate({ left: scrollcontainerpos.left + 'px' }, speed);
            }
        };

        this.scrollBottom = function (speed) {
            if (options.verticalscrollbar) {
                scrollcontent.animate({ scrollTop: scrollcontent.prop('scrollHeight') + 'px' }, speed);
                scrollbar.animate({ top: scrollcontainer.height() - scrollbar.height() + scrollcontainerpos.top + 'px' }, speed);
            }
            else {
                scrollcontent.animate({ scrollLeft: scrollcontent.prop('scrollWidth') + 'px' }, speed);
                scrollbar.animate({ left: scrollcontainer.width() - scrollbar.width() + scrollcontainerpos.left + 'px' }, speed);
            }
        };

        this.scrollTo = function (element, speed) {
            var elpos = element.offset();
            if (elpos == null) return;
            if (options.verticalscrollbar) {
                var scrolltop = elpos.top - (scrollcontent.height() / 2) + scrollcontent.scrollTop() - scrollcontainerpos.top - (ap ? scrollcontainer.offset().top : 0);
                scrollcontent.animate({ scrollTop: scrolltop + 'px' }, speed);
                scrollbar.animate({ top: scrollcontainerpos.top + scrolltop / (scrollcontent.prop('scrollHeight') / scrollcontent.height()) + "px" }, speed);
                if (scrollbar.offset().top + scrollbar.height() > scrollcontent.height()) scrollbar.css({ "top": scrollcontainer.height() - scrollbar.height() + scrollcontainerpos.top + "px" });
            }
            else {
                var scrollleft = elpos.left - (scrollcontent.width() / 2) + scrollcontent.scrollLeft() - scrollcontainerpos.left - (ap ? scrollcontainer.offset().left : 0);
                var scrollbarleft = scrollcontainerpos.left + scrollleft / (scrollcontent.prop('scrollWidth') / scrollcontent.width());
                if (scrollbarleft + scrollbar.width() > scrollcontent.width()) scrollbarleft = scrollcontainer.width() - scrollbar.width() + scrollcontainerpos.left;
                scrollcontent.animate({ scrollLeft: scrollleft + 'px' }, speed);
                scrollbar.animate({ left: scrollbarleft + "px" }, speed);
            }
        };

        //end public methods

        //init

        var defaults = {
            verticalscrollbar: false,
            horizontalscrollbar: false,
            container_class_name: 'slickscrollcontainer',
            vertical_scrollbar_class_name: 'slickscroll_vertical_scrollbar',
            horizontal_scrollbar_class_name: 'slickscroll_horizontal_scrollbar',
            min_scrollbar_size: 25,
            mousewheel_scroll_speed: 5
        }

        if (options != null) {
            if (options.verticalscrollbar == null) options.verticalscrollbar = defaults.verticalscrollbar;
            if (options.horizontalscrollbar == null) options.horizontalscrollbar = defaults.horizontalscrollbar;
            if (options.container_class_name == null) options.container_class_name = defaults.container_class_name;
            if (options.vertical_scrollbar_class_name == null) options.vertical_scrollbar_class_name = defaults.vertical_scrollbar_class_name;
            if (options.horizontal_scrollbar_class_name == null) options.horizontal_scrollbar_class_name = defaults.horizontal_scrollbar_class_name;
            if (options.min_scrollbar_size == null) options.min_scrollbar_size = defaults.min_scrollbar_size;
            if (options.mousewheel_scroll_speed == null) options.mousewheel_scroll_speed = defaults.mousewheel_scroll_speed;
        }
        else {
            options = defaults;
        }

        //$(document).unbind("mouseup");

        //end init

        //slickscroll logic
        return this.each(function () {
            scrollcontent = $(this);

            var scrollcontentparent = scrollcontent.parent();

            scrollhw = (options.verticalscrollbar ? scrollcontent.prop('scrollHeight') : scrollcontent.prop('scrollWidth'));

            scrollcontent.wrap('<div class="' + options.container_class_name + '"></div>'); //append the container
            scrollcontainer = scrollcontent.closest('.' + options.container_class_name); //get a ref to the container

            if (scrollcontent.css("position") == "absolute" || scrollcontent.css("position") == "relative") {
                ap = true;
                scrollcontainer.css({ "position": "relative"});
                scrollcontent.css({ "position": "relative" });
            }
            else if (scrollcontentparent.css("position") == "relative" || scrollcontentparent.css("position") == "absolute") {
                ap = true;
                //scrollcontainer.css({ "position": "absolute", "left": scrollcontentparent.offset().left + "px", "top": scrollcontentparent.offset().top + "px" });
                //scrollcontentparent.css({ "position": "static" });
            }

            if (options.verticalscrollbar) {
                scrollcontainer.prepend('<div class="' + options.vertical_scrollbar_class_name + '"><div></div></div>');
                scrollbar = scrollcontainer.children('.' + options.vertical_scrollbar_class_name);
            }
            else {
                scrollcontainer.prepend('<div class="' + options.horizontal_scrollbar_class_name + '"><div></div></div>');
                scrollbar = scrollcontainer.children('.' + options.horizontal_scrollbar_class_name);
            }

            scrollcontainer.mousedown(function (e) {
                if (options.verticalscrollbar) {
                    if (e.pageX < scrollbar.offset().left) return;
                    if (ap)
                        DoScroll(e, e.pageY - (scrollbar.height() / 2), null);
                    else
                        DoScroll(e, e.pageY - scrollcontentpos.top - (scrollbar.height() / 2), null);
                }
                else {
                    if (e.pageY < scrollbar.offset().top) return;
                    if (ap)
                        DoScroll(e, null, e.pageX - (scrollbar.width() / 2));
                    else
                        DoScroll(e, null, e.pageX - scrollcontentpos.left - (scrollbar.width() / 2));
                }
            });

            mousewheelscrolltop = scrollcontent.scrollTop();

            RecalcSize();
            SetIfScrollBarNeedsToBeVisible();

            scrollbar.unbind("mousedown");

            /*scrollcontent.mousewheel(function (e, delta) {
                if (options.verticalscrollbar) {
                    mousewheelscrolltop = scrollbar.offset().top - scrollcontentpos.top;
                    if (mousewheelscrolltop < 0) mousewheelscrolltop = 5;
                    if (mousewheelscrolltop + scrollbar.height() > scrollcontent.height()) mousewheelscrolltop = scrollcontent.height() - scrollbar.height() - options.mousewheel_scroll_speed;
                    mousewheelscrolltop += (delta < 0 ? options.mousewheel_scroll_speed : (options.mousewheel_scroll_speed * -1));
                    DoScroll(e, mousewheelscrolltop, null, true);
                }
                else {
                    mousewheelscrolltop = scrollbar.offset().left - scrollcontentpos.left;
                    if (mousewheelscrolltop < 0) mousewheelscrolltop = 5;
                    if (mousewheelscrolltop + scrollbar.width() > scrollcontent.width()) mousewheelscrolltop = scrollcontent.width() - scrollbar.width() - options.mousewheel_scroll_speed;
                    mousewheelscrolltop += (delta < 0 ? options.mousewheel_scroll_speed : (options.mousewheel_scroll_speed * -1));
                    DoScroll(e, null, mousewheelscrolltop, true);
                }
		return false
            });*/

            $(document).mouseup(function () {
                scrollbarmouseoffset = 0;
                $(document).unbind("mousemove");
                enableSelection(scrollcontent.get(0));
                enableSelection(document.body);
            });

            $(document).mouseleave(function () {
                $(document).unbind("mousemove");
                enableSelection(scrollcontent.get(0));
                enableSelection(document.body);
            });

            $(document).mousedown(function () {
                enableSelection(scrollcontent.get(0));
                enableSelection(document.body);
            });

            var scrollbarmouseoffset = 0;

            scrollbar.mousedown(function (e) {
                e.stopPropagation()
                $(document).unbind("mousemove");
                if (options.verticalscrollbar)
                    scrollbarmouseoffset = (e.pageY - scrollbar.offset().top);
                else
                    scrollbarmouseoffset = (e.pageX - scrollbar.offset().left);
                $(document).mousemove(function (e) {
                    DoScroll(e, null, null);
                });
            });

            function DoScroll(e, y, x, mw) {
                var scrollbarpos = scrollbar.offset();

                disableSelection(scrollcontent.get(0));
                disableSelection(document.body);

                if (options.verticalscrollbar) {
                    if (y == null) y = e.pageY - scrollcontainerpos.top - scrollbarmouseoffset;
                    if (ap && !mw) y -= scrollcontentpos.top
                    if (y >= 0) {
                        if (y + scrollbar.height() <= scrollcontainer.height()) {
                            scrollbar.css({ "top": y + scrollcontainerpos.top + "px" });
                            scrollcontent.scrollTop(y * (scrollcontent.prop('scrollHeight') / scrollcontent.height()));
                        }
                        else {
                            scrollbar.css({ "top": scrollcontainer.height() - scrollbar.height() + scrollcontainerpos.top + "px" });
                            scrollcontent.scrollTop(scrollcontent.prop('scrollHeight'));
                        }
                    }
                    else {
                        scrollbar.css({ "top": scrollcontainerpos.top + "px" });
                        scrollcontent.scrollTop(0);
                    }
                }
                else {
                    if (x == null) x = e.pageX - scrollcontainerpos.left - scrollbarmouseoffset;
                    if (ap && !mw) x -= scrollcontentpos.left
                    if (x >= 0) {
                        if (x + scrollbar.width() <= scrollcontainer.width()) {
                            scrollbar.css({ "left": x + scrollcontainerpos.left + "px" });
                            scrollcontent.scrollLeft(x * (scrollcontent.prop('scrollWidth') / scrollcontent.width()));
                        }
                        else {
                            scrollbar.css({ "left": scrollcontainer.width() - scrollbar.width() + scrollcontainerpos.left + "px" });
                            scrollcontent.scrollLeft(scrollcontent.prop('scrollWidth'));
                        }
                    }
                    else {
                        scrollbar.css({ "left": scrollcontainerpos.left + "px" });
                        scrollcontent.scrollLeft(0);
                    }
                }
            }

            $(window).resize(function () {
                RecalcSize();
                SetIfScrollBarNeedsToBeVisible();
            });

            function SetIfScrollBarNeedsToBeVisible() {
                if (options.verticalscrollbar) {
                    if (scrollhw <= scrollcontainer.height()) {
                        scrollbar.hide();
                        scrollcontainer.width(0);
                    }
                    else {
                        scrollbar.show();
                        scrollcontainer.width(null);
                    }
                }
                else {
                    if (scrollhw <= scrollcontainer.width()) {
                        scrollbar.hide();
                        scrollcontainer.height(0);
                    }
                    else {
                        scrollbar.show();
                        scrollcontainer.height(null);
                    }
                }
            }

            function RecalcSize() {

                scrollcontentpos = scrollcontent.offset();
                scrollcontainerpos = scrollcontainer.offset();

                if (ap) { scrollcontainerpos.left = 0; scrollcontainerpos.top = 0; }

                if (options.verticalscrollbar) {
                    scrollcontainer.css("width", '100%');
                    scrollcontent.css("width", ''); //resets the width to whatever is in the .css

                    scrollcontainer.width(scrollcontent.width() + scrollbar.width());
                    scrollcontent.width(scrollcontainer.width() - scrollbar.width());
                    if (ap)
                        scrollbar.height((scrollcontent.height() / scrollhw) * scrollcontent.height());
                    else
                        if (scrollcontent.prop('scrollHeight') != scrollcontent.height()) scrollbar.height((scrollcontent.height() / scrollcontent.prop('scrollHeight')) * scrollcontent.height());
                    scrollbar.css({ "left": scrollcontainerpos.left + scrollcontainer.width() - scrollbar.width() + "px" });
                    if (scrollbar.height() < options.min_scrollbar_size) scrollbar.height(options.min_scrollbar_size);
                }
                else {
                    scrollcontainer.css("width", '100%');
                    scrollcontent.css("width", 'auto'); //resets the width to whatever is in the .css

                    //scrollcontainer.width(scrollcontent.width());
                    //scrollcontent.width(scrollcontainer.width());
                    scrollcontainer.height(scrollcontent.height() + scrollbar.height());
                    if (ap)
                        scrollbar.width((scrollcontent.width() / scrollhw) * scrollcontent.width());
                    else
                        if (scrollcontent.prop('scrollWidth') != scrollcontent.width()) scrollbar.width((scrollcontent.width() / scrollcontent.prop('scrollWidth')) * scrollcontent.width());
                    scrollbar.css({ "top": scrollcontainer.outerHeight() - scrollbar.outerHeight()-5 + "px" });
                    if (scrollbar.width() < options.min_scrollbar_size) scrollbar.width(options.min_scrollbar_size);
                    if (scrollbar.offset().left + scrollbar.width() > scrollcontainerpos.left + scrollcontent.width()) scrollbar.css({ "left": scrollcontainerpos.left + scrollbar.offset().left - (scrollbar.offset().left + scrollbar.width() - scrollcontent.width()) + "px" });
                    if (scrollbar.offset().left < scrollcontainerpos.left) scrollbar.css({ "left": scrollcontainerpos.left });
                }
            }

        });

    }

})(jQuery);

//helper functions

function enableSelection(target) {

    if (typeof target.onselectstart != "undefined") //IE route
        target.onselectstart = function () { return true };

    if (typeof target.style.MozUserSelect != "undefined") //Firefox route
        target.style.MozUserSelect = "text";

    if (typeof target.style.KhtmlUserSelect != "undefined") //Firefox/chrome route
        target.style.KhtmlUserSelect = "text";

    target.onmousedown = function () { return true };

}

function disableSelection(target) {

    if (typeof target.onselectstart != "undefined") //IE route
        target.onselectstart = function () { return false };

    if (typeof target.style.MozUserSelect != "undefined") //Firefox route
        target.style.MozUserSelect = "none";

    if (typeof target.style.KhtmlUserSelect != "undefined") //Firefox/chrome route
        target.style.KhtmlUserSelect = "none";

    target.onmousedown = function () { return false };

}