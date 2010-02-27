/*
 * Copyright 2010 Shikhar Bhushan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file may incorporate work covered by the following copyright and
 * permission notice:
 *
 *     Licensed to the Apache Software Foundation (ASF) under one
 *     or more contributor license agreements.  See the NOTICE file
 *     distributed with this work for additional information
 *     regarding copyright ownership.  The ASF licenses this file
 *     to you under the Apache License, Version 2.0 (the
 *     "License"); you may not use this file except in compliance
 *     with the License.  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing,
 *      software distributed under the License is distributed on an
 *      "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *      KIND, either express or implied.  See the License for the
 *      specific language governing permissions and limitations
 *      under the License.
 */
package net.schmizz.sshj.userauth.method;

import net.schmizz.sshj.common.SSHPacket;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

// TODO check if this even works...!

/** Implements the {@code hostbased} SSH authentication method. */
public class AuthHostbased extends KeyedAuthMethod {

    protected final String hostname;
    protected final String hostuser;

    public AuthHostbased(KeyProvider kProv, String hostuser) {
        this(kProv, hostuser, null);
    }

    public AuthHostbased(KeyProvider kProv, String hostuser, String hostname) {
        super("hostbased", kProv);
        assert hostuser != null;
        this.hostuser = hostuser;
        this.hostname = hostname;
    }

    @Override
    protected SSHPacket buildReq() throws UserAuthException {
        SSHPacket req = putPubKey(super.buildReq());
        req.putString(hostname == null ? params.getTransport().getRemoteHost() : hostname) //
                .putString(hostuser);
        return putSig(req);
    }

}