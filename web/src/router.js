/**
 * Created by Administrator on 2017/8/15 0015.
 */
import React from 'react';
import { Router, Route } from 'dva/router';
import HomeLayout from './components/layouts/HomeLayout';

function requireRoute(cb, ...routes){
    require.ensure([], (require) => {
        for(let i in routes){
            cb(null, require("./routes/" + routes[i]));
        }
    });
}

function RouterConfig({ history, app}) {
    const routes=[
        {
            path:"/",
            getComponent(nextState, cb) {
                requireRoute(cb, 'App');
            },
            indexRoute:{
                getComponent(nextstate, cb){
                    requireRoute(cb,'LoginPage');
                },
            },
            childRoutes:[
              {
                component:HomeLayout,
                childRoutes:[
                    {
                        path: 'home',
                        getComponent(nextState, cb){
                            requireRoute(cb, 'HomePage');
                        },
                    },
                    {
                      path: 'user',
                      childRoutes: [
                        {
                          path: 'list',
                          getComponent(nextState, cb) {
                            requireRoute(cb, 'UserListPage');
                          }
                        }
                     ]
                    },
                  {
                    path: 'project',
                    childRoutes: [
                      {
                        path: 'list',
                        getComponent(nextState, cb) {
                          requireRoute(cb, 'ProjectListPage');
                        }
                      }
                    ]
                  },
                  {
                    path: 'dept',
                    childRoutes: [
                      {
                        path: 'list',
                        getComponent(nextState, cb) {
                          requireRoute(cb, 'DeptListPage');
                        },
                      }
                    ]
                  },
                    {
                        path: 'module',
                        childRoutes: [
                            {
                                path: 'list',
                                getComponent(nextState, cb) {
                                    requireRoute(cb, 'ModuleListPage');
                                }
                            }
                        ]
                    },
                    // {
                    //     path: 'interface',
                    //     childRoutes: [
                    //         {
                    //             path: 'info',
                    //             getComponent(nextState, cb) {
                    //                 requireRoute(cb, 'InterfaceInfoPage');
                    //             }
                    //         }
                    //     ]
                    // }
                ]
              }
            ]
        }
    ];

    return <Router history={history} routes={routes}/>;
}

export default RouterConfig;
